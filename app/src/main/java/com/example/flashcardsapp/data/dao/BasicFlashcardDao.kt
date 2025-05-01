package com.example.flashcardsapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.flashcardsapp.data.entities.BasicFlashcardEntity

@Dao
interface BasicFlashcardDao {
    @Insert
    suspend fun insert(flashcard: BasicFlashcardEntity)

    @Update
    suspend fun update(flashcard: BasicFlashcardEntity)

    @Delete
    suspend fun delete(flashcard: BasicFlashcardEntity)

    @Query("SELECT * FROM basic_flashcards")
    suspend fun getAllBasicFlashcards(): List<BasicFlashcardEntity>
}