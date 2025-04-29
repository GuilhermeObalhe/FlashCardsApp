package com.example.flashcardsapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun HeaderButtons(
    onBackClick: () -> Unit,
    onDeleteClick: (() -> Unit) ?= null,
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick
            ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                modifier = Modifier.size(36.dp)
            )

        }

        if (onDeleteClick != null) {
            IconButton(onClick = { /* deletar assunto */ }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Excluir assunto",
                    tint = Color(0xFFBF2E2E),
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}
