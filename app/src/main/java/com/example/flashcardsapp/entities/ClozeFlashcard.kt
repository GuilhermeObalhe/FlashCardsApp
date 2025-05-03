package com.example.flashcardsapp.entities

import kotlinx.serialization.Serializable

class ClozeFlashcard(
    val id: String,
    val subjectId: String,
    val text: String,
    val gaps: List<String>
)
