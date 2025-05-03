package com.example.flashcardsapp.ui.screens.createExercise

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.ui.components.Title
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateExerciseScreen(
    subjectId: Int,
    appViewModel: AppViewModel,
    onBackClick: () -> Unit,
    onNavigateToQuizCardCreate: () -> Unit,
    onNavigateToFlipCardCreate: () -> Unit,
    onNavigateToClozeExercise: () -> Unit
) {
    println("CreateExerciseScreen, assunto: $subjectId")
    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            item {
                HeaderButtons(
                    onBackClick = onBackClick,
                )

            }
            item {
                Title(text = "Criar Exercício")
                Spacer(modifier = Modifier.height(60.dp))
            }
            item {
                ExerciseTypeCard(
                    name = "Quiz",
                    onButtonClick = onNavigateToQuizCardCreate
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                ExerciseTypeCard(
                    name = "Básico",
                    onButtonClick = onNavigateToFlipCardCreate
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                ExerciseTypeCard(
                    name = "Cloze",
                    onButtonClick = onNavigateToClozeExercise
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


