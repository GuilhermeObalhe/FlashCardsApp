@file:Suppress("NAME_SHADOWING")
// Navegação
package com.example.flashcardsapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flashcardsapp.entities.QuizCard
import com.example.flashcardsapp.ui.screens.clozeScreen.ClozeCardCreateScreen
import com.example.flashcardsapp.ui.screens.createExercise.CreateExerciseScreen
import com.example.flashcardsapp.ui.screens.flipCardCreate.FlipCardCreateScreen
import com.example.flashcardsapp.ui.screens.flipCardExercise.FlipCardExerciseScreen
import com.example.flashcardsapp.ui.screens.homePage.AssuntosScreen
import com.example.flashcardsapp.ui.screens.quizScreen.QuizCardCreateScreen
import com.example.flashcardsapp.ui.screens.quizScreen.QuizCardExerciseScreen
import com.example.flashcardsapp.ui.screens.subjectPage.SubjectDetailScreen
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

@Composable
fun FlashCardsApp() {
    val navController = rememberNavController()
    val appViewModel: AppViewModel = viewModel()

    NavHost(navController = navController, startDestination = "assuntos") {


        composable("assuntos") {
            AssuntosScreen(
                viewModel = appViewModel,
                onNavigateToSubject = { subject ->
                    navController.navigate("subject_detail/${subject.id}")
                },
                locationsViewModel = appViewModel
            )
        }

        composable(
            route = "subject_detail/{subjectId}",
            arguments = listOf(navArgument("subjectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getInt("subjectId") ?: return@composable
            SubjectDetailScreen(
                subjectId = subjectId.toString(),
                appViewModel = appViewModel,
                onBackClick = { navController.popBackStack() },
                onNavigateToCreateExercise = { subjectId ->
                    navController.navigate("create_exercise/$subjectId")
                },
                navigateToFlipCardExerciseScreen = { exercise ->
                    val exerciseId = exercise.id
                    navController.navigate("flip_card/$subjectId/$exerciseId")
                },
                navigateToQuizCardExerciseScreen = { exercise ->
                    val exerciseId = exercise.id
                    navController.navigate("quiz_card/$subjectId/$exerciseId")
                }

            )
        }

        composable(
            route = "create_exercise/{subjectId}",
            arguments = listOf(navArgument("subjectId") { type = NavType.IntType })
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getInt("subjectId") ?: return@composable
            CreateExerciseScreen(
                subjectId = subjectId,
                appViewModel = appViewModel,
                onBackClick = { navController.popBackStack() },
                onNavigateToQuizCardCreate = {navController.navigate("quiz_card_create/$subjectId")},
                onNavigateToFlipCardCreate = {navController.navigate("flip_card_create/$subjectId")},
                onNavigateToClozeExercise = {navController.navigate("cloze_card_create/$subjectId")},

            )
        }
        composable("flip_card_create/{subjectId}") {
            val subjectId = it.arguments?.getString("subjectId")
            FlipCardCreateScreen(
                subjectId = subjectId!!.toInt(),
                onBackClick = { navController.popBackStack() },
                appViewModel = appViewModel,
                navigateToSubjectDetailScreen = {
                    navController.navigate("subject_detail/$subjectId")
                }
            )
        }

        composable("quiz_card_create/{subjectId}"){
            val subjectId = it.arguments?.getString("subjectId")
            QuizCardCreateScreen(
                subjectId = subjectId!!.toInt(),
                appViewModel = appViewModel,
                onBackClick = { navController.popBackStack() },
                navigateToSubjectDetailScreen = {
                    navController.navigate("subject_detail/$subjectId")
                }
            )
        }

        composable("cloze_card_create/{subjectId}"){
            val subjectId = it.arguments?.getString("subjectId")
            ClozeCardCreateScreen(
                subjectId = subjectId!!.toInt(),
                appViewModel = appViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("flip_card/{subjectId}/{exerciseId}"){
            val subjectId = it.arguments?.getString("subjectId")
            val exerciseId = it.arguments?.getString("exerciseId")
            val flashCardList = appViewModel.flashcardsBasic.filter { it.subjectId == subjectId }
            val basicFlashcard = flashCardList.find { it.id == exerciseId }

            if (basicFlashcard != null) {
                FlipCardExerciseScreen(
                    flashcard = basicFlashcard,
                    onBackClick = { navController.popBackStack() }
                )
            }

        }
        composable ("quiz_card/{subjectId}/{exerciseId}"){
            val subjectId = it.arguments?.getString("subjectId")
            val exerciseId = it.arguments?.getString("exerciseId")
            val flashCardList = appViewModel.flashcardQuiz.filter { it.subjectId == subjectId }
            val quizFlashcard = flashCardList.find { it.id == exerciseId }

            if (quizFlashcard != null) {
                QuizCardExerciseScreen(
                    quizCard = quizFlashcard,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

    }
}

