package com.example.flashcardsapp.network

import android.util.Log
import com.example.flashcardsapp.data.entities.BasicFlashcardEntity
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity
import com.example.flashcardsapp.data.entities.LocationEntity
import com.example.flashcardsapp.data.entities.QuizFlashcardEntity
import com.example.flashcardsapp.data.entities.SubjectEntity
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object FlashcardService {

    // Para a localização
    suspend fun postLocation(location: LocationEntity): Boolean {
        return try {
            val response: HttpResponse = ApiClient.client.post("${ApiClient.BASE_URL}/locations") {
                contentType(ContentType.Application.Json)
                setBody(location)
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteLocationByName(locationName: String) {
        try {
            // 1. Buscar localizações do servidor
            val response = ApiClient.client.get("${ApiClient.BASE_URL}/locations")
            val serverLocations: List<LocationEntity> = response.body()

            // 2. Encontrar a que tem o mesmo nome
            val matchingServerLocation = serverLocations.find { it.name == locationName }

            if (matchingServerLocation != null) {
                // 3. Obter o ID e fazer DELETE
                ApiClient.client.delete("${ApiClient.BASE_URL}/locations/${matchingServerLocation.id}")
                Log.d("API", "Localização '${locationName}' excluída com sucesso.")
            } else {
                Log.w("API", "Localização '${locationName}' não encontrada no servidor.")
            }
        } catch (e: Exception) {
            Log.e("API", "Erro ao excluir localização: ${e.message}")
        }
    }


    // Para Subjects

    // POST subject
    suspend fun postSubject(subject: SubjectEntity): Boolean {
        return try {
            val response: HttpResponse = ApiClient.client.post("${ApiClient.BASE_URL}/subjects") {
                contentType(ContentType.Application.Json)
                setBody(subject)
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    // DELETE subject pelo nome
    suspend fun deleteSubjectByName(subjectName: String) {
        try {
            // 1. Buscar subjects do servidor
            val response = ApiClient.client.get("${ApiClient.BASE_URL}/subjects")
            val serverSubjects: List<SubjectEntity> = response.body()

            // 2. Encontrar o que tem o mesmo nome
            val matchingServerSubject = serverSubjects.find { it.name == subjectName }

            if (matchingServerSubject != null) {
                // 3. Obter o ID e fazer DELETE
                ApiClient.client.delete("${ApiClient.BASE_URL}/subjects/${matchingServerSubject.id}")
                Log.d("API", "Assunto '${subjectName}' excluído com sucesso.")
            } else {
                Log.w("API", "Assunto '${subjectName}' não encontrado no servidor.")
            }
        } catch (e: Exception) {
            Log.e("API", "Erro ao excluir assunto: ${e.message}")
        }
    }


    // Para Basic Flashcard
    suspend fun postBasicFlashcard(flashcard: BasicFlashcardEntity): Boolean {
        return try {
            val response: HttpResponse = ApiClient.client.post("${ApiClient.BASE_URL}/flashcards/basic") {
                contentType(ContentType.Application.Json)
                setBody(flashcard)
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteBasicFlashcardByFront(front: String) {
        try {
            // 1. Buscar flashcards do servidor
            val response = ApiClient.client.get("${ApiClient.BASE_URL}/flashcards/basic")
            val serverFlashcards: List<BasicFlashcardEntity> = response.body()

            // 2. Encontrar o que tem o mesmo front
            val matchingFlashcard = serverFlashcards.find { it.front == front }

            if (matchingFlashcard != null) {
                // 3. Obter o ID e fazer DELETE
                ApiClient.client.delete("${ApiClient.BASE_URL}/flashcards/basic/${matchingFlashcard.id}")
                Log.d("API", "BasicFlashcard '${front}' excluído com sucesso.")
            } else {
                Log.w("API", "BasicFlashcard '${front}' não encontrado no servidor.")
            }
        } catch (e: Exception) {
            Log.e("API", "Erro ao excluir BasicFlashcard: ${e.message}")
        }
    }

    // Para Quiz Flashcard
    suspend fun postQuizFlashcard(flashcard: QuizFlashcardEntity): Boolean {
        return try {
            val response: HttpResponse = ApiClient.client.post("${ApiClient.BASE_URL}/flashcards/quiz") {
                contentType(ContentType.Application.Json)
                setBody(flashcard)
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteQuizFlashcardByQuestion(question: String) {
        try {
            // 1. Buscar flashcards do servidor
            val response = ApiClient.client.get("${ApiClient.BASE_URL}/flashcards/quiz")
            val serverFlashcards: List<QuizFlashcardEntity> = response.body()

            // 2. Encontrar o que tem a mesma question
            val matchingFlashcard = serverFlashcards.find { it.question == question }

            if (matchingFlashcard != null) {
                // 3. Obter o ID e fazer DELETE
                ApiClient.client.delete("${ApiClient.BASE_URL}/flashcards/quiz/${matchingFlashcard.id}")
                Log.d("API", "QuizFlashcard '${question}' excluído com sucesso.")
            } else {
                Log.w("API", "QuizFlashcard '${question}' não encontrado no servidor.")
            }
        } catch (e: Exception) {
            Log.e("API", "Erro ao excluir QuizFlashcard: ${e.message}")
        }
    }

    // Para Cloze Flashcard
    // POST cloze flashcard
    suspend fun postClozeFlashcard(flashcard: ClozeFlashcardEntity): Boolean {
        return try {
            val response: HttpResponse = ApiClient.client.post("${ApiClient.BASE_URL}/flashcards/cloze") {
                contentType(ContentType.Application.Json)
                setBody(flashcard)
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    // DELETE cloze flashcard pelo texto principal (text)
    suspend fun deleteClozeFlashcardByText(text: String) {
        try {
            // 1. Buscar flashcards do servidor
            val response = ApiClient.client.get("${ApiClient.BASE_URL}/flashcards/cloze")
            val serverFlashcards: List<ClozeFlashcardEntity> = response.body()

            // 2. Encontrar o que tem o mesmo text
            val matchingFlashcard = serverFlashcards.find { it.fullText == text }

            if (matchingFlashcard != null) {
                // 3. Obter o ID e fazer DELETE
                ApiClient.client.delete("${ApiClient.BASE_URL}/flashcards/cloze/${matchingFlashcard.id}")
                Log.d("API", "ClozeFlashcard '${text}' excluído com sucesso.")
            } else {
                Log.w("API", "ClozeFlashcard '${text}' não encontrado no servidor.")
            }
        } catch (e: Exception) {
            Log.e("API", "Erro ao excluir ClozeFlashcard: ${e.message}")
        }
    }



}
