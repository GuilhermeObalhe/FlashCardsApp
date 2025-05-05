package com.example.flashcardsapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.dao.BasicFlashcardDao
import com.example.flashcardsapp.data.dao.ClozeFlashcardDao
import com.example.flashcardsapp.data.dao.LocationDao
import com.example.flashcardsapp.data.dao.QuizFlashcardDao
import com.example.flashcardsapp.data.dao.SubjectDao
import com.example.flashcardsapp.data.entities.BasicFlashcardEntity
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity
import com.example.flashcardsapp.data.entities.LocationEntity
import com.example.flashcardsapp.data.entities.QuizFlashcardEntity
import com.example.flashcardsapp.data.entities.SubjectEntity
import com.example.flashcardsapp.entities.BasicFlashcard
import com.example.flashcardsapp.entities.ClozeFlashcard
import com.example.flashcardsapp.entities.Location
import com.example.flashcardsapp.entities.QuizCard
import com.example.flashcardsapp.entities.Subject
import com.example.flashcardsapp.network.FlashcardService
import com.example.flashcardsapp.network.FlashcardService.deleteBasicFlashcardByFront
import com.example.flashcardsapp.network.FlashcardService.deleteClozeFlashcardByText
import com.example.flashcardsapp.network.FlashcardService.deleteQuizFlashcardByQuestion
import com.example.flashcardsapp.network.FlashcardService.deleteSubjectByName
import com.example.flashcardsapp.network.FlashcardService.postBasicFlashcard
import com.example.flashcardsapp.network.FlashcardService.postClozeFlashcard
import com.example.flashcardsapp.network.FlashcardService.postQuizFlashcard
import com.example.flashcardsapp.network.FlashcardService.postSubject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val locationDao: LocationDao,
    private val subjectDao: SubjectDao,
    private val flashcardBasicDao: BasicFlashcardDao,
    private val flashcardQuizDao: QuizFlashcardDao,
    private val flashcardClozeDao: ClozeFlashcardDao
) : ViewModel() {


    //
    //
    // ASSUNTOS: coleta, adição e exclusão
    //
    //

    val subjectsFromDb: StateFlow<List<SubjectEntity>> =
        subjectDao.getAllSubjects()
            .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun saveSubject(name: String) {
        val newSubject = SubjectEntity(name = name)
        viewModelScope.launch {
            subjectDao.insert(newSubject)
            postSubject(newSubject)
        }
    }

    fun deleteSubject(subject: SubjectEntity) {
        viewModelScope.launch {
            subjectDao.delete(subject)
            deleteSubjectByName(subject.name)
        }
    }

    //
    //
    // BasicFlashcard: coleta, adição e exclusão
    //
    //
    val flascardsBasicFromDb: StateFlow<List<BasicFlashcardEntity>> =
        flashcardBasicDao.getAllBasicFlashcards()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    fun saveFlashcardBasic(subjectId: String, question: String, answer: String) {
        var newFlashcard = BasicFlashcardEntity(
            subjectId = subjectId.toLong(),
            front = question,
            back = answer,
            lastLocationId = selectedLocation.value?.id,
            reviewTime = 0
        )
        viewModelScope.launch {
            flashcardBasicDao.insert(newFlashcard)
            newFlashcard = BasicFlashcardEntity(
                subjectId = subjectId.toLong(),
                front = question,
                back = answer,
                lastLocationId = selectedLocation.value?.id,
                reviewTime = 0
            )
            postBasicFlashcard(newFlashcard)
        }

    }

    fun deleteFlashcardBasic(BasicFlashcard: BasicFlashcardEntity){
        viewModelScope.launch {
            flashcardBasicDao.delete(
                BasicFlashcard
            )
            deleteBasicFlashcardByFront(BasicFlashcard.front)
        }
    }

    //
    //
    // QuizFlashcard: coleta, adição e exclusão
    //
    //

    val flashcardQuizFromDb: StateFlow<List<QuizFlashcardEntity>> =
        flashcardQuizDao.getAllQuizFlashcards()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun saveFlashcardQuiz(subjectId: String, question: String, options: List<String>, correctAnswerIndex: Int) {
        val newFlashcard = QuizFlashcardEntity(
            subjectId = subjectId.toLong(),
            question = question,
            options = options,
            correctIndex = correctAnswerIndex,
            lastLocationId = selectedLocation.value?.id,
            reviewTime = 0
            )
        viewModelScope.launch {
            flashcardQuizDao.insertQuizFlashcard(newFlashcard)
            postQuizFlashcard(newFlashcard)
        }
    }
    fun deleteFlashcardQuiz(QuizFlashcard: QuizFlashcardEntity){
        viewModelScope.launch {
            flashcardQuizDao.deleteQuizFlashcard(
                QuizFlashcard
            )
            deleteQuizFlashcardByQuestion(QuizFlashcard.question)
        }
    }

    //
    //
    // ClozeFlashcard: coleta, adição e exclusão
    //
    //

    val flashcardClozeFromDb: StateFlow<List<ClozeFlashcardEntity>> =
        flashcardClozeDao.getAllClozeFlashcards()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun saveFlashcardCloze(subjectId: String, text: String, gaps: List<String>) {
        val newFlashcard = ClozeFlashcardEntity(
            subjectId = subjectId.toLong(),
            fullText = text,
            gaps = gaps,
            lastLocationId = selectedLocation.value?.id,
            reviewTime = 0,
            )
        viewModelScope.launch {
            flashcardClozeDao.insertClozeFlashcard(newFlashcard)
            postClozeFlashcard(newFlashcard)
        }

    }

    fun deleteFlashcardCloze(ClozeFlashcard: ClozeFlashcardEntity){
        viewModelScope.launch {
            flashcardClozeDao.deleteClozeFlashcard(
                ClozeFlashcard
            )
            deleteClozeFlashcardByText(ClozeFlashcard.fullText)
        }
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

    val flashcardCloze = mutableStateListOf<ClozeFlashcard>()
    fun addClozeCard(subjectId: String, text: String, gaps: List<String>) {
        val id = (flashcardCloze.size + 1).toString()
        val clozeCard = ClozeFlashcard(id, subjectId, text, gaps)
        flashcardCloze.add(clozeCard)
    }

    val locations = mutableStateListOf<LocationEntity>()

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
            FlashcardService.postLocation(newLocation)
        }
    }

    // Teste para excluir as localizações do banco de dados
    fun deleteLocation(location: LocationEntity) {
        viewModelScope.launch {
            locationDao.delete(location)
            FlashcardService.deleteLocationByName(location.name)
        }
    }
}