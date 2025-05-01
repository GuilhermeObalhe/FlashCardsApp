package com.example.flashcardsapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flashcardsapp.data.entities.BasicFlashcardEntity
import com.example.flashcardsapp.data.entities.QuizFlashcardEntity
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity
import com.example.flashcardsapp.data.entities.LocationEntity
import com.example.flashcardsapp.data.dao.BasicFlashcardDao
import com.example.flashcardsapp.data.dao.QuizFlashcardDao
import com.example.flashcardsapp.data.dao.ClozeFlashcardDao
import com.example.flashcardsapp.data.dao.LocationDao
import com.example.flashcardsapp.data.dao.SubjectDao
import com.example.flashcardsapp.data.entities.SubjectEntity

@Database(
    entities = [
        BasicFlashcardEntity::class,
        QuizFlashcardEntity::class,
        ClozeFlashcardEntity::class,
        LocationEntity::class,
        SubjectEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class) // <- registra os conversores personalizados
abstract class FlashcardDatabase : RoomDatabase() {
    abstract fun basicFlashcardDao(): BasicFlashcardDao
    abstract fun quizFlashcardDao(): QuizFlashcardDao
    abstract fun clozeFlashcardDao(): ClozeFlashcardDao
    abstract fun locationDao(): LocationDao
    abstract fun subjectDao(): SubjectDao
}
