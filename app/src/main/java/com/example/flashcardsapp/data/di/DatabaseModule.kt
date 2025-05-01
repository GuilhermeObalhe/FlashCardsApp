package com.example.flashcardsapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.flashcardsapp.data.FlashcardDatabase
import com.example.flashcardsapp.data.dao.BasicFlashcardDao
import com.example.flashcardsapp.data.dao.ClozeFlashcardDao
import com.example.flashcardsapp.data.dao.LocationDao
import com.example.flashcardsapp.data.dao.QuizFlashcardDao
import com.example.flashcardsapp.data.dao.SubjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Módulo para fazer a injeção de dependência
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FlashcardDatabase {
        return Room.databaseBuilder(
            context,
            FlashcardDatabase::class.java,
            "flashcard_database"
        ).build()
    }

    @Provides
    fun provideLocationDao(database: FlashcardDatabase): LocationDao {
        return database.locationDao()
    }

    @Provides
    fun provideQuizFlashcardDao(database: FlashcardDatabase): QuizFlashcardDao {
        return database.quizFlashcardDao()
    }

    @Provides
    fun provideClozeFlashcardDao(database: FlashcardDatabase): ClozeFlashcardDao {
        return database.clozeFlashcardDao()
    }

    @Provides
    fun provideBasicFlashcardDao(database: FlashcardDatabase): BasicFlashcardDao {
        return database.basicFlashcardDao()
    }

    @Provides
    fun provideSubjectDao(database: FlashcardDatabase): SubjectDao {
        return database.subjectDao()
    }
    // Adicione outros DAOs se quiser injetar na ViewModel
}