package com.example.flashcardsapp.ui.screens.homePage

import PoppinsRegular
import PoppinsSemiBold
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.ui.components.RoundedOutlinedButton
import com.example.flashcardsapp.ui.components.Title

@Composable
fun OptionsMenuOverlay(
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    println()

    Column(
        modifier = Modifier
            .fillMaxSize()

    )
    {
            Box(
                modifier = Modifier
                    .weight(1f) // ocupa todo o espaço livre
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.25f))
                    .clickable {onDismiss()}

            )
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            ){
                Column(
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 40.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    onDelete()
                                    onDismiss()
                                }


                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Deletar assunto",
                                    tint = Color.Red,
                                    modifier = Modifier.padding(end = 10.dp)
                                )
                                Text(
                                    text = "Excluir assunto",
                                    fontFamily = PoppinsSemiBold,
                                    fontSize = 20.sp,
                                    color = Color.Red
                                )
                            }
                        }
                    }

                }
            }
        }
    }

