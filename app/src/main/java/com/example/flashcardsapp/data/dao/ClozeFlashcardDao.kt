package com.example.flashcardsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity

@Dao
interface ClozeFlashcardDao {

    @Insert
    suspend fun insertClozeFlashcard(flashcard: ClozeFlashcardEntity)

    @Update
    suspend fun updateClozeFlashcard(flashcard: ClozeFlashcardEntity)

    @Delete
    suspend fun deleteClozeFlashcard(flashcard: ClozeFlashcardEntity)

    @Query("SELECT * FROM cloze_flashcards")
    suspend fun getAllClozeFlashcards(): List<ClozeFlashcardEntity>
}