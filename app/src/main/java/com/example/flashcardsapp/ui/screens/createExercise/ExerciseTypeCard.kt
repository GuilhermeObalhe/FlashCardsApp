package com.example.flashcardsapp.ui.screens.createExercise


import PoppinsBold
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.entities.Subject


@Composable
fun ExerciseTypeCard(
    name: String,
    onButtonClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(1.dp, Color.White, RoundedCornerShape(20.dp)),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Button(
            onClick = onButtonClick ?: {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(20.dp) // corrige "corner" → "shape"
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color(0xFF595B5A),
                    fontFamily = PoppinsBold,
                    modifier = Modifier
                        .padding(start = 30.dp)
                        .weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Ir para assunto",
                    tint = Color(0XFF2EBF96),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 30.dp)
                        .size(32.dp)
                )
            }
        }
    }
}
