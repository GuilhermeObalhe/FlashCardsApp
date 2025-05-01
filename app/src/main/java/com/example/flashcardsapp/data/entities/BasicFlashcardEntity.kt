package com.example.flashcardsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "basic_flashcards")
@Serializable
data class BasicFlashcardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: Int,
    val question: String,
    val answer: String,
    val lastLocationId: Int?,
    val reviewTime: Long
)
