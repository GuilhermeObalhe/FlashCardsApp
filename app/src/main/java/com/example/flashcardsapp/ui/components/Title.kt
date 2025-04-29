package com.example.flashcardsapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.R

// ─────────────────────────────────────────────────────────────
// 📄 Title.kt
// Este componente exibe um título estilizado com a fonte Capriola.
// Ele também pode exibir opcionalmente um ícone de ação (como "+").
// É comumente usado no topo de telas para indicar o contexto,
// como "Assuntos", "Favoritos", etc.
// ─────────────────────────────────────────────────────────────

val Capriola = FontFamily(Font(R.font.capriola_regular)) // Fonte personalizada

@Composable
fun Title(
    text: String,
    subtitle: String? = null,
    icon: ImageVector? = null, // Ícone opcional à direita do título
    onIconClick: (() -> Unit)? = null, // Ação ao clicar no ícone
    subtitleClickable: (() -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Alinha o texto à esquerda e o ícone à direita
        ) {
            Text(
                text = text, // Texto do título
                fontSize = 36.sp,
                fontFamily = Capriola,
                color = Color(0xFF034B36)
            )

            // Exibe o ícone apenas se ambos (ícone e ação) forem fornecidos
            if (icon != null && onIconClick != null) {
                IconButton(
                    onClick = onIconClick
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Ícone de ação",
                        tint = Color(0xFF008C6D),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

        }
        Row {
            if (subtitle != null) {


                Text(
                    text = subtitle,
                    fontSize = 24.sp,
                    fontFamily = Capriola,
                    color = Color(0xFF595B5A),
                    modifier = Modifier.clickable {
                        if(subtitleClickable != null){
                            subtitleClickable()
                        }
                    }
                )

            }
        }
    }
}
