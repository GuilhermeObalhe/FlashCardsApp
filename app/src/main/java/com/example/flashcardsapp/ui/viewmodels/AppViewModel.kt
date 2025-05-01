package com.example.flashcardsapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.dao.LocationDao
import com.example.flashcardsapp.data.entities.LocationEntity
import com.example.flashcardsapp.entities.BasicFlashcard
import com.example.flashcardsapp.entities.Location
import com.example.flashcardsapp.entities.QuizCard
import com.example.flashcardsapp.entities.Subject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val locationDao: LocationDao) : ViewModel() {

    // Lista de assuntos (mockada)
    val subjects = mutableStateListOf(
        Subject("1", "Português"),
        Subject("2", "História")
    )


    // Adiciona novo assunto
    fun addSubject(name: String) {
        val id = (subjects.size + 1).toString()
        subjects.add(Subject(id, name))
    }

    // Remove assunto e os exercícios associados
    fun removeSubject(subject: Subject) {
        subjects.remove(subject)
        //Remover todos os exercicios do assunto
    }

    // Lista de flashcards basic mockados
    val flashcardsBasic = mutableStateListOf<BasicFlashcard>(
        BasicFlashcard("1", "1", "Qual é a capital do Brasil?", "Brasília"),
        BasicFlashcard("2", "1", "Quem pintou a Mona Lisa?", "Leonardo da Vinci"),
    )

    fun addFlashcardBasic(subjectId: String, front: String, back: String) {
        val id = (flashcardsBasic.size + 1).toString()
        flashcardsBasic.add(BasicFlashcard(id, subjectId, front, back))
        Log.d("Na ViewModel", "Tamanho = ${flashcardsBasic.size}")
    }

    val flashcardQuiz = mutableStateListOf<QuizCard>(
        QuizCard("1", "1", "Qual é a capital do Brasil?", listOf("São Paulo", "Rio de Janeiro", "Brasília", "Belo Horizonte"), 3),
        QuizCard("2", "1", "Quem pintou a Mona Lisa?", listOf("Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo"), 1),
        QuizCard("3", "1", "Qual é o maior planeta do sistema solar?", listOf("Vênus", "Marte", "Júpiter", "Terra"), 3),


    )
    fun addQuizCard(subjectId: String, question: String, options: List<String>, correctAnswerIndex: Int) {
        val id = (flashcardQuiz.size + 1).toString()
        val quizCard = QuizCard(id, subjectId, question, options, correctAnswerIndex)
        flashcardQuiz.add(quizCard)
    }


    val locations = mutableStateListOf(
        Location("1", "Quarto"),
        Location("2", "Biblioteca"),
        Location("3", "Ônibus")
    )

    val selectedLocation = mutableStateOf<LocationEntity?>(null)


    fun selectLocation(location: LocationEntity) {
        selectedLocation.value = location
    }

    // Adições feitas para testar o banco

    // Teste para mostrar as localizações no banco de dados
    val locationsFromDb: StateFlow<List<LocationEntity>> =
        locationDao.getLocations() // DAO deve retornar Flow<List<LocationEntity>>
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    fun showLocations() {
        viewModelScope.launch {
            locationDao.getLocations().collect { locations ->
                locations.forEach {
                    Log.d("Location", "ID: ${it.id}, Name: ${it.name}")
                }
            }
        }
    }

    // Teste para salvar a localização no banco de dados
    fun saveLocation(name: String) {
        val newLocation = LocationEntity(name = name)
        viewModelScope.launch {
            locationDao.insert(newLocation)
        }
    }

    // Teste para excluir as localizações do banco de dados
    fun deleteLocation(location: LocationEntity) {
        viewModelScope.launch {
            locationDao.delete(location)
        }
    }
}