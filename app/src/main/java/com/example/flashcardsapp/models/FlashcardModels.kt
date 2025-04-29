package com.example.flashcardsapp.models

import kotlinx.serialization.Serializable

@Serializable
sealed class Flashcard {
    abstract val id: Int?
    abstract val subjectId: Int
    abstract val type: String
}

@Serializable
data class BasicFlashcard(
    override val id: Int? = null,
    override val subjectId: Int,
    val question: String,
    val answer: String,
    override val type: String = "basic"
) : Flashcard()

@Serializable
data class ClozeFlashcard(
    override val id: Int? = null,
    override val subjectId: Int,
    val fullText: String,
    val hiddenPart: String,
    override val type: String = "cloze"
) : Flashcard()

@Serializable
data class QuizFlashcard(
    override val id: Int? = null,
    override val subjectId: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    override val type: String = "quiz"
) : Flashcard()

