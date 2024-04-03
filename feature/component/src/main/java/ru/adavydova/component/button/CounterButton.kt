package ru.adavydova.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.adavydova.component.font.BodyTwo


@Composable
fun CounterButton(
    numOfSelectItem: Int,
    onClickMinus: () -> Unit,
    onClickPlus: () -> Unit,
    modifier: Modifier = Modifier,
    colorContainer: Color = Color.White,
    sizeButton: Dp = 40.dp
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(sizeButton)
            .fillMaxWidth()
    ) {

        ButtonMinus(
            colorContainer = colorContainer,
            sizeButton = sizeButton,
            onClick = {
                onClickMinus()
            }
        )
        BodyTwo(
            lineHeight = 16.sp,
            text = numOfSelectItem.toString()
        )
        ButtonPlus(
            colorContainer = colorContainer,
            sizeButton = sizeButton,
            onClick = { onClickPlus() }
        )
    }

}



