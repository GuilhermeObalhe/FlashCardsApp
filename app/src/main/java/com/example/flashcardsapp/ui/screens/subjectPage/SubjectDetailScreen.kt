package com.example.flashcardsapp.ui.screens.subjectPage

import PoppinsRegular
import PoppinsSemiBold
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcardsapp.data.entities.BasicFlashcardEntity
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity
import com.example.flashcardsapp.data.entities.QuizFlashcardEntity
import com.example.flashcardsapp.entities.BasicFlashcard
import com.example.flashcardsapp.entities.ClozeFlashcard
import com.example.flashcardsapp.entities.QuizCard
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.ui.components.OptionsMenuOverlay
import com.example.flashcardsapp.ui.components.Title
import com.example.flashcardsapp.ui.viewmodels.AppViewModel
import kotlinx.coroutines.flow.filter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubjectDetailScreen(
    subjectId: String,
    appViewModel: AppViewModel,
    onBackClick: () -> Unit,
    onNavigateToCreateExercise: (String) -> Unit,
    navigateToFlipCardExerciseScreen: (BasicFlashcardEntity) -> Unit,
    navigateToQuizCardExerciseScreen: (QuizFlashcardEntity) -> Unit,
    navigateToClozeCardExerciseScreen: (ClozeFlashcardEntity) -> Unit,
    navigateToHome: ()-> Unit
) {
    val subject = appViewModel.subjectsFromDb.value.find { it.id == subjectId.toLong() } ?: return

    // Obter a lista de flashcards aqui
    val basicFlashcards by appViewModel.flascardsBasicFromDb.collectAsState()
    val listaBasicFlashcard = basicFlashcards.filter {
        it.subjectId == subject.id
    }

    val quizFlashcards by appViewModel.flashcardQuizFromDb.collectAsState()
    val listaQuizFlashcard = quizFlashcards.filter {
        it.subjectId == subjectId.toLong()
    }

    val clozeFlashcards by appViewModel.flashcardClozeFromDb.collectAsState()
    val listaClozeFlashcard = clozeFlashcards.filter {
        it.subjectId == subjectId.toLong()
    }

    val selectedBasicExercise = remember{
        mutableStateOf<BasicFlashcardEntity?>(null)
    }

    val selectedQuizExercise = remember{
        mutableStateOf<QuizFlashcardEntity?>(null)
    }

    val selectedClozeExercise = remember{
        mutableStateOf<ClozeFlashcardEntity?>(null)
    }

    val isOptionsOpen = remember {
        mutableStateOf(false)
    }



    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            item {
                HeaderButtons(onBackClick = navigateToHome)
            }

            item {
                Title(
                    text = subject.name,
                    icon = Icons.Default.AddCircle,
                    onIconClick = {
                        onNavigateToCreateExercise(subjectId)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }

            // Mensagem se não houver flashcards
            if (listaBasicFlashcard.isEmpty()
                && listaQuizFlashcard.isEmpty()
                && listaClozeFlashcard.isEmpty()) {
                item {
                    Text(
                        text = "Adicione seu primeiro exercício clicando no '+'.",
                        fontSize = 20.sp,
                        fontFamily = PoppinsRegular,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }else {
                item {
                    if (listaBasicFlashcard.isNotEmpty()) {
                    Text(
                        text = "Flip Flashcards",
                        fontFamily = PoppinsSemiBold,
                        fontSize = 24.sp,
                        color = Color(0xFF505050)
                    )
                    Spacer(
                        modifier = Modifier.height(
                            20.dp
                        )
                    )
                    }
                }
                // Preencher com os exercícios de acordo com a matéria
                Log.d("Na tela Subject", "tamanho = ${listaBasicFlashcard.size}")
                items(listaBasicFlashcard) { item ->
                    ExerciseCard(
                        text = item.front,
                        onButtonClick = { navigateToFlipCardExerciseScreen(item) },
                        onLongClick = {
                            selectedBasicExercise.value = item
                            isOptionsOpen.value = true
                        }
                    )
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            }

            if(!listaQuizFlashcard.isEmpty()){
                item {
                    if (listaQuizFlashcard.isNotEmpty()) {
                        Text(
                            text = "Quiz Flashcards",
                            fontFamily = PoppinsSemiBold,
                            fontSize = 24.sp,
                            color = Color(0xFF505050)
                        )
                        Spacer(
                            modifier = Modifier.height(
                                20.dp
                            )
                        )
                    }
                }
                items(listaQuizFlashcard) { item ->
                    ExerciseCard(
                        text = item.question,
                        onButtonClick = { navigateToQuizCardExerciseScreen(item) },
                        onLongClick = {
                            selectedQuizExercise.value = item
                            isOptionsOpen.value = true
                        }

                    )
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            }
            if(!listaClozeFlashcard.isEmpty()){
                item {
                    if (listaClozeFlashcard.isNotEmpty()) {
                        Text(
                            text = "Cloze Flashcards",
                            fontFamily = PoppinsSemiBold,
                            fontSize = 24.sp,
                            color = Color(0xFF505050)
                        )
                        Spacer(
                            modifier = Modifier.height(
                                20.dp
                            )
                        )
                    }
                }
                items(listaClozeFlashcard) { item ->
                    ExerciseCard(
                        text = item.fullText,
                        onButtonClick = { navigateToClozeCardExerciseScreen(item) },
                        onLongClick = {
                            selectedClozeExercise.value = item
                            isOptionsOpen.value = true
                        }

                    )
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }
            }
            item {
                Spacer(
                    modifier = Modifier.height(40.dp)
                )
            }
        }
        if (selectedBasicExercise.value != null) {
            OptionsMenuOverlay(
                onDismiss = {
                    isOptionsOpen.value = false
                    selectedBasicExercise.value = null
                },
                onDelete = {
                    appViewModel.deleteFlashcardBasic(selectedBasicExercise.value!!)
                    selectedBasicExercise.value = null
                }
            )
        }else if(selectedQuizExercise.value != null){
            OptionsMenuOverlay(
                onDismiss = {
                    isOptionsOpen.value = false
                    selectedQuizExercise.value = null
                },
                onDelete = {
                    appViewModel.deleteFlashcardQuiz(selectedQuizExercise.value!!)
                    selectedQuizExercise.value = null
                }
            )
        }else if(selectedClozeExercise.value != null){
            OptionsMenuOverlay(
                onDismiss = {
                    isOptionsOpen.value = false
                    selectedClozeExercise.value = null
                },
                onDelete = {
                    appViewModel.deleteFlashcardCloze(selectedClozeExercise.value!!)
                    selectedClozeExercise.value = null
                }
            )
        }
    }

}