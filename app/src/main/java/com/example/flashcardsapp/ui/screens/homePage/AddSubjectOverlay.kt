package com.example.flashcardsapp.ui.screens.homePage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcardsapp.data.entities.SubjectEntity
import com.example.flashcardsapp.ui.components.RoundedOutlinedButton
import com.example.flashcardsapp.ui.components.Title
import com.example.flashcardsapp.ui.viewmodels.AppViewModel

@Composable
fun AddSubjectOverlay(
    isOpen: MutableState<Boolean>,
    appViewModel: AppViewModel,

) {
    val newSubjectName = remember { mutableStateOf("") }

    if (isOpen.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            /* overlay sem foco… */
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.25f))
                .clickable { isOpen.value = false }
            )
            /* painel de diálogo */
            Box(modifier = Modifier
                .height(420.dp)
                .background(Color.White)
            ) {
                Column(modifier = Modifier.padding(40.dp)) {
                    Title("Novo Assunto")
                    Spacer(modifier = Modifier.height(40.dp))
                    OutlinedTextField(
                        value = newSubjectName.value,
                        onValueChange = { newSubjectName.value = it },
                        label = { Text("Nome do Assunto") },
                        textStyle = TextStyle(fontSize = 20.sp),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(Color.Black)
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    RoundedOutlinedButton(
                        text = "Adicionar",
                        onClick = {
                            val name = newSubjectName.value.trim()
                            if (name.isNotEmpty()) {
                                appViewModel.saveSubject(name)
                                isOpen.value = false
                            }
                        }
                    )
                }
            }
        }
    }
}



