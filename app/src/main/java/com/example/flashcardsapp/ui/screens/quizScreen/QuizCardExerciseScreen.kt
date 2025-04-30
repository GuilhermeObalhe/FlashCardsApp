package com.example.flashcardsapp.ui.screens.quizScreen


import PoppinsRegular
import PoppinsSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.entities.QuizCard
import com.example.flashcardsapp.ui.components.HeaderButtons

@Composable
fun QuizCardExerciseScreen(
    onBackClick: () -> Unit,
    quizCard: QuizCard
){
    val question = quizCard.question
    val options = quizCard.options
    val correctAnswer = quizCard.correctAnswerIndex - 1

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(-1) }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 40.dp)
    ) {
        item {
            HeaderButtons(
                onBackClick = onBackClick
            )
        }
        item {
        if (selectedOption != -1) {
            val isCorrect = selectedOption == correctAnswer
            val backgroundColor = if (isCorrect) Color(0xFFB9FBC0) else Color(0xFFFFC9B9)
            val borderColor = if (isCorrect) Color(0xFF2EBF96) else Color(0xFFFF6B6B)
            val message = if (isCorrect) "Resposta correta!" else "Resposta incorreta."

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp)
                    .background(backgroundColor, RoundedCornerShape(20.dp))
                    .border(2.dp, borderColor, RoundedCornerShape(20.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = message,
                    fontSize = 18.sp,
                    fontFamily = PoppinsSemiBold,
                    color = Color(0xFF034B36),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
            }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(420.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(20.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFF034B36))
            ){

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = question,
                        fontSize = 24.sp,
                        fontFamily = PoppinsRegular,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .alpha(1f)
                    )
                }

            }
        }


        items(options.size) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onOptionSelected(index) }
            ) {
                RadioButton(
                    selected = selectedOption == index,
                    onClick = { onOptionSelected(index) }
                )
                Text(
                    text = options[index],
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }


    }



}
