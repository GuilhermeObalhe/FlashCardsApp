package com.example.flashcardsapp.ui.screens.homePage

import PoppinsRegular
import PoppinsSemiBold
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.data.entities.LocationEntity
import com.example.flashcardsapp.entities.Subject
import com.example.flashcardsapp.ui.components.SubjectCard
import com.example.flashcardsapp.ui.components.Title
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun AssuntosScreen(
    onNavigateToSubject: (Subject) -> Unit,
    viewModel: AppViewModel,
    locationsViewModel: AppViewModel,
) {
    val showAddDialog = remember { mutableStateOf(false) }
    val isAddSubjectOpen = remember { mutableStateOf(false) }
    val isMenuOpen = remember { mutableStateOf(false) }
    val isOptionsOpen = remember { mutableStateOf(false) }
    val selectedSubject = remember { mutableStateOf<Subject?>(null) }


    Scaffold {
        LazyColumn(modifier = Modifier.padding(horizontal = 40.dp)) {
            item {
                MenuButton(onClick = { isMenuOpen.value = true })
            }

            item {
                Title(
                    text = "Assuntos",
                    subtitle = when {
                        locationsViewModel.locations.isEmpty() -> "Nenhuma localização criada"
                        locationsViewModel.selectedLocation.value == null -> "Nenhuma localização selecionada"
                        else -> locationsViewModel.selectedLocation.value!!.name
                    },
                    icon = Icons.Default.AddCircle,
                    onIconClick = { isAddSubjectOpen.value = true },
                    subtitleClickable = {isMenuOpen.value = true}
                )
            }

            item {
                Spacer(modifier = Modifier.height(60.dp))
            }

            if (viewModel.subjects.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ainda não há assuntos adicionados  \uD83D\uDE22",
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 20.sp,
                            fontFamily = PoppinsSemiBold,
                            color = Color(0xFF595B5A),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Você pode adicionar assuntos clicando no botão '+'",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            fontSize = 15.sp,
                            fontFamily = PoppinsRegular,
                            color = Color(0xFF595B5A),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(viewModel.subjects) { subject ->
                    Column {
                        SubjectCard(
                            subject = subject,
                            onClick = { onNavigateToSubject(subject) },
                            onLongClick = {
                                selectedSubject.value = subject
                                isOptionsOpen.value = true
                            },
                            onDelete = {
                                viewModel.removeSubject(subject)
                            },
                            isOptionsMenuOpen = selectedSubject.value?.id == subject.id
                        )


                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }

    // Menu lateral
    MenuOverlay(
        isOpen = isMenuOpen,
        locations = locationsViewModel.locations.map { LocationEntity(it.id.toLong(), it.name) },
        selectedLocation = locationsViewModel.selectedLocation.value?.let {
            LocationEntity(it.id, it.name)
        },
        onSelectLocation = {
            locationsViewModel.selectLocation(LocationEntity(it.id, it.name))
            isMenuOpen.value = false
        },
        onAddLocationClick = { showAddDialog.value = true },
        onAddLocation = { locationsViewModel.saveLocation(it) },
        onRemoveLocation = {
            locationsViewModel.deleteLocation(it)
        }
    )

    // Adição de assunto
    if (isAddSubjectOpen.value) {
        AddSubjectOverlay(
            isOpen = isAddSubjectOpen,
            appViewModel = viewModel
        )
    }

    // Diálogo de nova localização
    if (showAddDialog.value) {
        AddLocationAlert(
            showDialog = showAddDialog,
            onConfirm = {
                locationsViewModel.saveLocation(it)
                locationsViewModel.selectLocation(locationsViewModel.locationsFromDb.value[locationsViewModel.locationsFromDb.value.size - 1])
            }
        )
    }
    if (selectedSubject.value != null) {
        OptionsMenuOverlay(
            onDismiss = {
                isOptionsOpen.value = false
                selectedSubject.value = null
            },
            onDelete = {
                viewModel.removeSubject(selectedSubject.value!!)
                selectedSubject.value = null
            }
        )
    }

}