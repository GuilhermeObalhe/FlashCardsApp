package com.example.flashcardsapp.ui.screens.homePage

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
import com.example.flashcardsapp.entities.Location
import com.example.flashcardsapp.ui.components.Title

@Composable
fun MenuOverlay(
    isOpen: MutableState<Boolean>,
    locations: List<Location>,
    selectedLocation: Location?,
    onSelectLocation: (Location) -> Unit,
    onAddLocationClick: () -> Unit,
    onAddLocation: (String) -> Unit,
    onRemoveLocation: (Location) -> Unit,
) {
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
                    locations = locations,
                    selectedLocation = selectedLocation,
                    onAddClick = { showDialog.value = true },
                    onSelect = onSelectLocation,
                    onRemove = onRemoveLocation
                )

                if (showDialog.value) {
                    AddLocationAlert(
                        showDialog = showDialog,
                        onConfirm = {
                            onAddLocation(it)
                        }
                    )
                }
            }
        }
    }
