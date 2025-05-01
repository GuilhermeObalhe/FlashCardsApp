package com.example.flashcardsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "quiz_flashcards")
@Serializable
data class QuizFlashcardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Int,
    val question: String,
    val options: List<String>, // Armazena as opções de resposta
    val correctIndex: Int,
    val lastLocationId: Int?,
    val reviewTime: Long
)

// options deu problema pq List<String> não é aceitado pelo Room. Logo, devo criar uma classe
// Para converters os tipos