package com.example.flashcardsapp.ui.screens.homePage

import PoppinsBold
import PoppinsRegular
import PoppinsSemiBold
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flashcardsapp.R
import com.example.flashcardsapp.data.entities.LocationEntity
import com.example.flashcardsapp.entities.Location
import com.example.flashcardsapp.ui.viewmodels.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AccordionSection(
    title: String,
    selectedLocation: LocationEntity?,
    onAddClick: () -> Unit,
    onRemove: (LocationEntity) -> Unit,
    onSelect: (LocationEntity) -> Unit,
) {
    val appViewModel: AppViewModel = hiltViewModel()
    val locations by appViewModel.locationsFromDb.collectAsState()
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var showAlert by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = { isExpanded = !isExpanded })
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontFamily = PoppinsSemiBold,
            fontSize = 20.sp,
            color = Color(0xFF595B5A)
        )
        IconButton(onClick = { isExpanded = !isExpanded }) {
            if (isExpanded)
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
            else
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
    }

    if (isExpanded) {

        locations.forEach { location ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(location) }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = location.name,
                    modifier = Modifier.weight(1f),
                    fontFamily = PoppinsRegular,
                    fontSize = 16.sp,
                    color = if (location == selectedLocation) Color(0xFF034B36) else Color(0xFF595B5A)
                )
                IconButton(onClick = { onRemove(location) }) {
                    Icon(Icons.Default.Close, contentDescription = "Remover")
                }
            }
        }

        if (showAlert) {
            AlertSpan(text = "Limite atingido") {
                showAlert = false
            }
        }

        Button(
            onClick = {
                if (locations.size < 7) {
                    onAddClick()
                    showAlert = false
                } else {
                    showAlert = true
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF034B36)),
            elevation = ButtonDefaults.buttonElevation(5.dp),
            modifier = Modifier
                .border(1.dp, Color.White, RoundedCornerShape(100.dp))
                .fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar")
            Text(
                "Nova Localização",
                modifier = Modifier.padding(start = 10.dp),
            )
        }
    }
}

@Composable
fun AlertSpan(
    text: String,
    onDismiss: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        visible = false
        delay(300)
        onDismiss()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300, easing = androidx.compose.animation.core.FastOutSlowInEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300, easing = androidx.compose.animation.core.FastOutSlowInEasing)),
        modifier = Modifier.padding(vertical = 20.dp),
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .border(1.dp, Color(0xFF420000), RoundedCornerShape(20.dp))
                .height(60.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD05F5F)),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    fontFamily = PoppinsBold,
                    fontSize = 12.sp,
                    color = Color(0xFF420000),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}