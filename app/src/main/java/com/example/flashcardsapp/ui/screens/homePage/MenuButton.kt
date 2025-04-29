package com.example.flashcardsapp.ui.screens.homePage

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// 📄 MenuButton.kt
// Botão com ícone de menu (hambúrguer) que pode receber uma ação de clique.
// Usado no topo da tela para abrir o menu lateral (MenuOverlay).
// ─────────────────────────────────────────────────────────────

@Composable
fun MenuButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp)
    ) {
        IconButton(
            onClick = onClick // Agora o botão chama uma função externa
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
