package com.example.flashcardsapp.ui.screens.flipCardExercise

import PoppinsBold
import PoppinsRegular
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.components.Title
import kotlin.math.abs

@Composable
fun FlipCard(
    front: String,
    back: String
) {
    var flipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        label = "cardRotation"
    )

    val shape = RoundedCornerShape(20.dp)

    // Define a cor com base na rotação
    val cardColor by remember(rotation) {
        derivedStateOf {
            if (rotation <= 90f) Color(0xFF2EBF96) else Color(0xFF034B36)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(420.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable { flipped = !flipped }
            .border(1.dp, Color.White, shape),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Flip Icon",
            tint = Color.White,
            modifier = Modifier.padding(10.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (rotation <= 90f) {
                Text(
                    text = front,
                    fontSize = 24.sp,
                    fontFamily = PoppinsRegular,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .alpha(1f)
                )
            } else {
                Text(
                    text = back,
                    fontSize = 24.sp,
                    fontFamily = PoppinsBold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer { rotationY = 180f } // inverte texto do verso
                        .alpha(1f)
                )
            }
        }
    }
}

