package com.example.flashcardsapp.ui.screens.clozeScreen

import PoppinsBold
import PoppinsRegular
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity
import com.example.flashcardsapp.entities.ClozeFlashcard
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.ui.components.RoundedOutlinedButton
import com.example.flashcardsapp.ui.viewmodels.AppViewModel
import java.util.*

@Composable
fun ClozeCardCreateScreen(
    subjectId: String,
    onBackClick: () -> Unit,
    appViewModel: AppViewModel,
    onNavigateToSubjectDetailScreen: () -> Unit
) {
    var annotatedText by remember { mutableStateOf(AnnotatedString("")) }
    var fullText by remember { mutableStateOf(TextFieldValue("")) }
    var gaps = remember { mutableStateListOf<String>() }
    var showDialog by remember { mutableStateOf(false) }
    var newGap by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 60.dp)
    ) {
        HeaderButtons(onBackClick = onBackClick)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Novo Exercicio",
            fontSize = 28.sp,
            fontFamily = PoppinsBold,
            color = Color(0xFF034B36)
        )
        Text(
            text = "Cloze",
            fontSize = 16.sp,
            fontFamily = PoppinsRegular,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Enunciado",
            fontSize = 18.sp,
            fontFamily = PoppinsBold,
            color = Color(0xFF4A4A4A)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = fullText,
            onValueChange = {
                fullText = it
            },
            placeholder = { Text("Digite o texto do enunciado") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Lista de lacunas
        gaps.forEachIndexed { index, word ->
            Text(
                text = "${index + 1} - $word",
                fontSize = 14.sp,
                fontFamily = PoppinsRegular,
                color = Color(0xFF2EBF96),
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Botão para adicionar lacuna
        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2EBF96)),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(text = "+ Inserir lacuna", color = Color.White)
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Botão principal
        RoundedOutlinedButton(
            text = "Adicionar",
            onClick = {

                appViewModel.saveFlashcardCloze(subjectId.toString(), fullText.text, gaps )
                onNavigateToSubjectDetailScreen()

            }
        )
    }

    // Diálogo de nova lacuna
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Inserir lacuna") },
            text = {
                OutlinedTextField(
                    value = newGap,
                    onValueChange = { newGap = it },
                    placeholder = { Text("Digite a lacuna") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newGap.isNotBlank()) {
                        val updatedText = fullText.text + " ${newGap}"
                        fullText = TextFieldValue(updatedText)
                        gaps.add(newGap)
                    }
                    newGap = ""
                    showDialog = false
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

