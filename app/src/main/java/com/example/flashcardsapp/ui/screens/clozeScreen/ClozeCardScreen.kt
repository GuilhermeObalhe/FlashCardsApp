package com.example.flashcardsapp.ui.screens.clozeScreen

import PoppinsBold
import PoppinsRegular
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flashcardsapp.data.entities.ClozeFlashcardEntity
import com.example.flashcardsapp.entities.ClozeFlashcard
import com.example.flashcardsapp.ui.components.Capriola
import com.example.flashcardsapp.ui.components.HeaderButtons
import com.example.flashcardsapp.ui.components.RoundedOutlinedButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ClozeCardScreen(
    flashcard: ClozeFlashcardEntity,
    onBackClick: () -> Unit
) {
    val userAnswers = remember { mutableStateListOf(*Array(flashcard.gaps.size) { "" }) }
    val feedback = remember { mutableStateListOf<String>() }


    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(40.dp))
            HeaderButtons(onBackClick = onBackClick)
            Spacer(modifier = Modifier.height(30.dp))
        }
        item {
            Text(
                text = "Exercício ${flashcard.id}",
                fontFamily = Capriola,
                fontSize = 36.sp,
                color = Color(0xFF034B36)
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(Color(0xFF2EBF96)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(40.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    val parts = splitTextByGaps(flashcard.fullText, flashcard.gaps)

                    FlowRow(
                        verticalArrangement = Arrangement.Center,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        parts.forEach { part ->
                            when (part) {
                                is ClozeTextPart.Text -> {
                                    Text(
                                        text = part.value,
                                        fontSize = 18.sp,
                                        fontFamily = PoppinsRegular,
                                        color = Color.White,
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .alignByBaseline(),
                                        lineHeight = 10.sp
                                    )
                                }

                                is ClozeTextPart.Gap -> {
                                    val index = part.index
                                    BasicTextField(
                                        value = userAnswers[index],
                                        onValueChange = { userAnswers[index] = it },
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(20.dp)
                                            .alignByBaseline(),
                                        textStyle = TextStyle(
                                            fontSize = 18.sp,
                                            color = Color.White,
                                            textAlign = TextAlign.Center
                                        ),
                                        singleLine = true,
                                        decorationBox = { innerTextField ->
                                            Box(
                                                modifier = Modifier
                                                    .width(100.dp)
                                                    .height(40.dp)
                                                    .padding(horizontal = 4.dp, vertical = 0.dp)
                                                    .drawBehind {
                                                        // Borda inferior
                                                        drawLine(
                                                            color = Color.White,
                                                            start = Offset(0f, size.height),
                                                            end = Offset(size.width, size.height),
                                                            strokeWidth = 2.dp.toPx()
                                                        )
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                innerTextField()
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
        item {
            if (feedback.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    feedback.forEach { message ->
                        val isCorrect = message.startsWith("✅")
                        val background = if (isCorrect) Color(0xFFB9FBC0) else Color(0xFFFFC9B9)
                        val border = if (isCorrect) Color(0xFF2EBF96) else Color(0xFFFF6B6B)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .border(2.dp, border, RoundedCornerShape(12.dp))
                                .background(background, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = message,
                                fontFamily = PoppinsRegular,
                                fontSize = 16.sp,
                                color = Color(0xFF034B36)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            RoundedOutlinedButton(
                text = "Responder",
                onClick = {
                    feedback.clear()
                    flashcard.gaps.forEachIndexed { index, gap ->
                        val expectedAnswer = gap
                        val userAnswer = userAnswers[index]
                        if (userAnswer.equals(expectedAnswer, ignoreCase = true)) {
                            feedback.add("✅ A resposta para a lacuna '$expectedAnswer' está correta!")
                        } else {
                            feedback.add("❌ A resposta para a lacuna ${index+1} está incorreta. A resposta correta é '$expectedAnswer'.")
                        }

                    }
                }


            )
            Spacer(modifier = Modifier.height(40.dp))
        }


    }
}
sealed class ClozeTextPart {
    data class Text(val value: String) : ClozeTextPart()
    data class Gap(val index: Int) : ClozeTextPart()
}

fun splitTextByGaps(text: String, gaps: List<String>): List<ClozeTextPart> {
    val result = mutableListOf<ClozeTextPart>()
    var remaining = text
    var gapIndex = 0

    while (gapIndex < gaps.size) {
        val gap = gaps[gapIndex]
        val gapStart = remaining.indexOf(gap)

        if (gapStart == -1) break

        if (gapStart > 0) {
            result.add(ClozeTextPart.Text(remaining.substring(0, gapStart)))
        }

        result.add(ClozeTextPart.Gap(gapIndex))

        remaining = remaining.substring(gapStart + gap.length)
        gapIndex++
    }

    if (remaining.isNotEmpty()) {
        result.add(ClozeTextPart.Text(remaining))
    }

    return result
}



