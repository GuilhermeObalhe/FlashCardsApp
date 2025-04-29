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
    onBackClick: () -> Unit
) {
    var question by remember { mutableStateOf("") }
    var options: MutableList<String> = remember { mutableStateListOf("", "") }

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
            Title(text = "Novo Exercicio", subtitle = "Quiz")

            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            Text(
                text = "Questão",
                fontSize = 24.sp,
                fontFamily = PoppinsSemiBold,
                color = Color(0xFF505050),
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                placeholder = { Text("Digite a questão") },
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

        items(options.size) { index ->
            val option = options[index]
            OutlinedTextField(
                value = option,
                onValueChange = { newValue ->
                    options[index] = newValue
                },
                placeholder = { Text("Alternativa ${index + 1}") },
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

        item {
            RoundedOutlinedButton(
                text = "Adicionar Alternativa",
                onClick = {
                    options.add("")
                }
            )

            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            RoundedOutlinedButton(
                text = "Criar Quiz",
                onClick = {
                    // Adicione a lógica para salvar o quiz
                    // Exemplo:
                    // appViewModel.addQuiz(subjectId.toString(), question, options)
                    onBackClick()
                }
            )
        }
    }
}