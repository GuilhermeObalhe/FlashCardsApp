package com.example.flashcardsapp.ui.screens.clozeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

@Composable
fun ClozeCardCreateScreen(
    subjectId: Int,
    appViewModel: AppViewModel,
    onBackClick: () -> Unit
) {
    var fullText by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(horizontal = 40.dp, vertical = 60.dp)
        .fillMaxSize()) {

        HeaderButtons(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = fullText,
            onValueChange = { fullText = it },
            label = { Text("Texto Completo (a lógica de cloze ainda será implementada)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Resposta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                // Lógica para salvar o cloze card
                // appViewModel.addClozeCard(ClozeCard(fullText, answer))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Cloze Card")
        }
    }
}