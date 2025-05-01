package com.example.flashcardsapp.ui.screens.homePage

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flashcardsapp.data.entities.LocationEntity
import com.example.flashcardsapp.ui.components.Title
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

// Acrescentei o parâmetro appViewModel nessa classe para poder fazer o teste de salvar no banco
@Composable
fun MenuOverlay(
    isOpen: MutableState<Boolean>,
    locations: List<LocationEntity>,
    selectedLocation: LocationEntity?,
    onSelectLocation: (LocationEntity) -> Unit,
    onAddLocationClick: () -> Unit,
    onAddLocation: (String) -> Unit,
    onRemoveLocation: (LocationEntity) -> Unit
) {
    val appViewModel: AppViewModel = hiltViewModel()
    val showDialog = remember { mutableStateOf(false) }
    if (!isOpen.value) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.25f))
            .clickable { isOpen.value = false },
    )
        Box(
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight()
                .background(Color.White, shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Spacer(modifier = Modifier.height(120.dp))
                Title(text = "Olá!")
                Spacer(modifier = Modifier.height(20.dp))

                AccordionSection(
                    title = "Localizações",
                    selectedLocation = selectedLocation,
                    onAddClick = { showDialog.value = true },
                    onSelect = onSelectLocation,
                    onRemove = { location -> appViewModel.deleteLocation(location) }
                )

                if (showDialog.value) {
                    AddLocationAlert(
                        showDialog = showDialog,
                        onConfirm = {
                            Log.d("MenuOverlay", "Botão apertado")
                            // O botão de adicionar no menu de loc é aqui
                            // Teste para salvar no banco
                            appViewModel.saveLocation(it)
                            Log.d("Conteúdo do banco:", "${appViewModel.showLocations()}")
                        }
                    )
                }
            }
        }
    }
