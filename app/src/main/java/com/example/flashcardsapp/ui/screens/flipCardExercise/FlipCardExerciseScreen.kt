package com.example.flashcardsapp.ui.screens.flipCardExercise

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.models.BasicFlashcard

@Composable
fun FlipCardExerciseScreen(
    flashcard: BasicFlashcard,
    onBackClick: () -> Unit

){

    LazyColumn(
        modifier = Modifier.padding(horizontal = 40.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(60.dp))
            HeaderButtons(onBackClick = onBackClick)

        }
        item {
            FlipCard(flashcard.question, flashcard.answer)
        }
    }
}

@Preview
@Composable
fun FlipCardExercise(){
    FlipCardExerciseScreen(
        onBackClick = {},
        flashcard = BasicFlashcard(
            id = 1,
            subjectId = 1,
            question = "Qual é a capital do Brasil?",
            answer = "Brasilia"
        )
    )
}