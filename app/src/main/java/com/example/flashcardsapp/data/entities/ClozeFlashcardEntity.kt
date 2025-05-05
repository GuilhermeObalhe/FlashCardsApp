package com.example.flashcardsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "cloze_flashcards")
@Serializable
data class ClozeFlashcardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val subjectId: Long,
    val fullText: String, // Texto completo com lacunas
    val gaps: List<String>, // Parte que deve ser preenchida
    val lastLocationId: Long?,
    val reviewTime: Long
)
