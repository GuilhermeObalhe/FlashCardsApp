package com.example.flashcardsapp.entities

import java.io.Serializable

class BasicFlashcard(val id: String, val subjectId: String, val question: String, val answer: String) : Serializable