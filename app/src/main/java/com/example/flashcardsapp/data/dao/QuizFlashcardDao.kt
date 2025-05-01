package com.example.flashcardsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flashcardsapp.data.entities.QuizFlashcardEntity

@Dao
interface QuizFlashcardDao {
    @Insert
    suspend fun insertQuizFlashcard(flashcard: QuizFlashcardEntity)

    @Update
    suspend fun updateQuizFlashcard(flashcard: QuizFlashcardEntity)

    @Delete
    suspend fun deleteQuizFlashcard(flashcard: QuizFlashcardEntity)

    @Query("SELECT * FROM quiz_flashcards")
    suspend fun getAllQuizFlashcards(): List<QuizFlashcardEntity>
}