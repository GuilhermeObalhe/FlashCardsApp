package com.example.flashcardsapp.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.flashcardsapp.entities.BasicFlashcard
import com.example.flashcardsapp.entities.Location
import com.example.flashcardsapp.entities.Subject
import java.util.UUID

class AppViewModel : ViewModel() {

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
    val flashcardsBasic = mutableStateListOf(
        BasicFlashcard("1", "1", "Teste", "teste"),
        BasicFlashcard("2", "1", "Teste2", "teste2"),
        BasicFlashcard("3", "2", "Teste3", "teste3")
    )

    fun addFlashcardBasic(subjectId: String, front: String, back: String) {
        val id = (flashcardsBasic.size + 1).toString()
        flashcardsBasic.add(BasicFlashcard(id, subjectId, front, back))
        Log.d("Na ViewModel", "Tamanho = ${flashcardsBasic.size}")
    }

    val locations = mutableStateListOf(
        Location("1", "Quarto"),
        Location("2", "Biblioteca"),
        Location("3", "Ônibus")
    )

    val selectedLocation = mutableStateOf<Location?>(null)

    fun addLocation(name: String) {
        val id = UUID.randomUUID().toString()
        locations.add(Location(id, name))
        selectedLocation.value = locations.last()
    }

    fun removeLocation(location: Location) {
        locations.remove(location)
        if (selectedLocation.value == location) {
            selectedLocation.value = null
        }
    }

    fun selectLocation(location: Location) {
        selectedLocation.value = location
    }

}