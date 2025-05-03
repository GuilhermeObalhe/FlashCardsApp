package com.example.flashcardsapp.data

import androidx.room.TypeConverter
import com.example.flashcardsapp.data.entities.BasicFlashcardEntity
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity
import com.example.flashcardsapp.data.entities.QuizFlashcardEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(value)
    }

}