package ru.adavydova.component.font

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.adavydova.component.R

@Composable
fun BodyOne(
    text: String,
    modifier: Modifier = Modifier,
    fontSize : TextUnit = 16.sp,
    lineHeight: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.W400,
    color: Color = Color.Black,
    textDecoration: TextDecoration = TextDecoration.None
) {
    Text(
        textDecoration = textDecoration,
        modifier = modifier,
        lineHeight = lineHeight,
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        text = text
    )
}

@Composable
fun BodyTwo(
    text: String,
    modifier: Modifier = Modifier,
    lineHeight: TextUnit = 24.sp,
    maxLine:Int = 10,
    fontSize : TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.W500,
    color: Color = Color.Black
) {
    Text(
        modifier = modifier,
        maxLines = maxLine,
        lineHeight = lineHeight,
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        text = text
    )
}