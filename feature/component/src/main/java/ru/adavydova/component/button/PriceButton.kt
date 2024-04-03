package ru.adavydova.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.adavydova.catalog_feature.dimensions.BUTTON_BUY_HEIGHT
import ru.adavydova.component.font.BodyOne
import ru.adavydova.component.font.BodyTwo


@Composable
fun PriceStateButton(
    price: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    oldPrice: Int? = null
) {
    when (oldPrice) {
        null -> {
            PriceButton(
                modifier = modifier,
                price = price,
                onClick = onClick
            )
        }
        else -> {
            PriceButtonWithOldValue(
                modifier = modifier,
                price = price,
                onClick = onClick,
                oldPrice = oldPrice
            )
        }
    }
}

@Composable
fun PriceButton(
    price: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    oldPrice: Int? = null
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(BUTTON_BUY_HEIGHT),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {

        BodyTwo(text = "$price ₽")
    }
}

@Composable
fun PriceButtonWithOldValue(
    price: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    oldPrice: Int
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(BUTTON_BUY_HEIGHT),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            BodyTwo(text = "$price ₽")
            BodyOne(
                color = Color.Black.copy(alpha = .6f),
                textDecoration = TextDecoration.LineThrough,
                text = "$oldPrice ₽"
            )
        }
    }
}
