package com.example.flashcardsapp.models

import kotlinx.serialization.Serializable

//@Serializable
//sealed class Flashcard {
//    abstract val id: Int?
//    abstract val subjectId: Int
//    abstract val type: String
//    abstract val lastLocationId: Int? // Acrescentei esse atributo
//    abstract val reviewTime: Long   // Acrescentei esse atributo também
//}

/* Tirei a classe mãe Flashcard.
Tive que acrescentar os atributos lastLocationId e reviewTime em cada data class.
Fiz isso porque cada flashcard deve saber onde ele foi resolvido pela última vez (quarto, biblioteca, etc)
e quando deve ser revisado (daqui 3 dias, 4 dias, etc).
 */

@Serializable
data class BasicFlashcard(
    val id: Int? = null,
    val subjectId: Int,
    val question: String,
    val answer: String,
    val type: String = "basic",
    val lastLocationId: Int?,
    val reviewTime: Long
)

@Serializable
data class ClozeFlashcard(
    val id: String,
    val subjectId: String,
    val fullText: String,
    val gaps: String,
    val type: String = "cloze",
    val lastLocationId: Int?,
    val reviewTime: Long
)

@Serializable
data class QuizFlashcard(
    val id: String,
    val subjectId: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val type: String = "quiz",
    val lastLocationId: Int?,
    val reviewTime: Long
)