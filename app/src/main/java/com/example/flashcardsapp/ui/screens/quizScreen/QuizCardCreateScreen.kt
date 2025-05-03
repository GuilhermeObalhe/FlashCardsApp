package com.example.flashcardsapp.ui.screens.quizScreen

import PoppinsSemiBold
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.ui.components.RoundedOutlinedButton
import com.example.flashcardsapp.ui.components.Title
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MutableCollectionMutableState")
@Composable
fun QuizCardCreateScreen(
    subjectId: Int,
    appViewModel: AppViewModel,
    onBackClick: () -> Unit,
    navigateToSubjectDetailScreen: () -> Unit
) {
    var question by remember { mutableStateOf("") }
    val options = remember { mutableStateListOf("", "", "", "") }
    var selectedAnswer by remember { mutableStateOf(1) }
    var expanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 60.dp)
    ) {
        item {
            HeaderButtons(onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Title(text = "Novo Exercicio", subtitle = "Alternativa")
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            Text(
                text = "Nome",
                fontSize = 24.sp,
                fontFamily = PoppinsSemiBold,
                color = Color(0xFF505050)
            )
            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                placeholder = { Text("Qual sua pergunta?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF034B36),
                    unfocusedBorderColor = Color(0xFF034B36)
                )
            )
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            Text(
                text = "Alternativas",
                fontSize = 24.sp,
                fontFamily = PoppinsSemiBold,
                color = Color(0xFF505050)
            )
            Spacer(modifier = Modifier.height(20.dp))

            options.forEachIndexed { index, option ->
                OutlinedTextField(
                    value = option,
                    onValueChange = { options[index] = it },
                    placeholder = { Text("Alternativa nº ${index + 1}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF034B36),
                        unfocusedBorderColor = Color(0xFF034B36)
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        item {
            Text(
                text = "Qual a resposta correta?",
                fontSize = 16.sp,
                color = Color(0xFF505050),
                modifier = Modifier.padding(bottom = 10.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedAnswer.toString(),
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(20.dp),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .width(80.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF034B36),
                        unfocusedBorderColor = Color(0xFF034B36)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    (1..4).forEach { index ->
                        DropdownMenuItem(
                            text = { Text(index.toString()) },
                            onClick = {
                                selectedAnswer = index
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        item {
            RoundedOutlinedButton(
                text = "Adicionar",
                onClick = {
                    appViewModel.saveFlashcardQuiz(
                        subjectId = subjectId.toString(),
                        question = question,
                        options = options,
                        correctAnswerIndex = selectedAnswer - 1
                    )
                    navigateToSubjectDetailScreen()
                }
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
