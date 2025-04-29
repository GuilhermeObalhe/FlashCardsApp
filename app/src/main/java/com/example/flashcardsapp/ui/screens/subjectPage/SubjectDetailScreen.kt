package com.example.flashcardsapp.ui.screens.subjectPage

import PoppinsRegular
import PoppinsSemiBold
import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.ui.components.Title
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SubjectDetailScreen(
    subjectId: String,
    appViewModel: AppViewModel,
    onBackClick: () -> Unit,
    onNavigateToCreateExercise: (String) -> Unit
) {
    val subject = appViewModel.subjects.find { it.id == subjectId } ?: return

    // Obter a lista de flashcards aqui
    val lista = appViewModel.flashcardsBasic.filter { it.subjectId == subjectId }


    Scaffold {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            item {
                HeaderButtons(onBackClick = onBackClick)
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
            if (lista.isEmpty()) {
                item {
                    Text(
                        text = "Adicione seu primeiro exercício clicando no '+'.",
                        fontSize = 20.sp,
                        fontFamily = PoppinsRegular,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            // Preencher com os exercícios de acordo com a matéria
            Log.d("Na tela Subject", "tamanho = ${lista.size}")
            items(lista) {
                ExerciseCard()
            }
        }
    }
}