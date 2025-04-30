package com.example.flashcardsapp.entities

import java.io.Serializable

data class QuizCard(
    val id: String,
    val subjectId: String,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
) : Serializable
