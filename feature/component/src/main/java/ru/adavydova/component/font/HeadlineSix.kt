package ru.adavydova.component.font

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HeadlineSix(
    text: String,
    color: Color = Color.Black.copy(alpha = .87f)
) {
    Text(
        lineHeight = 24.sp,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        color = color,
        text = text
    )
}