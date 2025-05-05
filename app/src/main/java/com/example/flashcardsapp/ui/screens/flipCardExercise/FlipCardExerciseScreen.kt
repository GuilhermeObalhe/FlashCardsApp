package com.example.flashcardsapp.ui.screens.flipCardExercise

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcardsapp.data.entities.BasicFlashcardEntity
import com.example.flashcardsapp.entities.BasicFlashcard
import com.example.flashcardsapp.ui.components.HeaderButtons

@Composable
fun FlipCardExerciseScreen(
    flashcard: BasicFlashcardEntity,
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
            FlipCard(flashcard.front, flashcard.back)
        }
    }
}

