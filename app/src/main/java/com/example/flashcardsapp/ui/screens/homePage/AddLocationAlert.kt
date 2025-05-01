package com.example.flashcardsapp.ui.screens.homePage

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.ui.components.Capriola
import com.example.flashcardsapp.ui.components.Title

@Composable
fun AddLocationAlert(
    showDialog: MutableState<Boolean>,
    onConfirm: (String) -> Unit
) {
    val newLocationName = remember { mutableStateOf("") }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                newLocationName.value = ""
            },
            title = { Text(
                "Nova Localização",
                fontFamily = Capriola,
                color = Color(0xFF034B36),
                fontSize = 24.sp
            ) },
            text = {
                OutlinedTextField(
                    value = newLocationName.value,
                    onValueChange = { newLocationName.value = it },
                    label = { Text("Nome do local") },
                    textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                    maxLines = 1,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                    val name = newLocationName.value.trim()
                    if (name.isNotEmpty()) {
                        onConfirm(name)
                        showDialog.value = false
                        newLocationName.value = ""
                    }
                    },

                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFF034B36))
                ) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    newLocationName.value = ""
                    },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color(0xFFD05F5F))

                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
