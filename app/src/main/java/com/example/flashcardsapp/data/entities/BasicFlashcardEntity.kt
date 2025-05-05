package com.example.flashcardsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "basic_flashcards")
@Serializable
data class BasicFlashcardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val subjectId: Long,
    val front: String,
    val back: String,
    val lastLocationId: Long?,
    val reviewTime: Long
)
