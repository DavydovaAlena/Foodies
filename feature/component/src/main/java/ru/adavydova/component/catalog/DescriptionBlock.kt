package ru.adavydova.component.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.adavydova.component.button.CounterButton
import ru.adavydova.component.button.PriceButton
import ru.adavydova.component.button.PriceStateButton


@Composable
fun DescriptionBlockProductCard(
    name: String,
    weight: String,
    price: Int,
    modifier: Modifier = Modifier,
    oldPrice: Int? = null,
    addItemToCart: () ->Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Text(
                text = name,
                maxLines = 2,
                fontWeight = FontWeight.W400,
                color = Color.Black.copy(alpha = .87f),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = weight,
                maxLines = 1,
                fontWeight = FontWeight.W400,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = Color.Black.copy(alpha = .6f)
            )
        }

        PriceStateButton(
            modifier = Modifier
                .fillMaxWidth(),
            price = price,
            oldPrice = oldPrice ,
            onClick = addItemToCart)

    }
}

@Composable
fun DescriptionBlockProductCard(
    name: String,
    weight: String,
    count: Int,
    modifier: Modifier = Modifier,
    onClickAddItem: () -> Unit,
    onClickRemoveItem: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {
            Text(
                text = name,
                maxLines = 2,
                fontWeight = FontWeight.W400,
                color = Color.Black.copy(alpha = .87f),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = weight,
                maxLines = 1,
                fontWeight = FontWeight.W400,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = Color.Black.copy(alpha = .6f)
            )
        }

        CounterButton(
            numOfSelectItem = count,
            onClickMinus = onClickRemoveItem,
            onClickPlus = onClickAddItem
        )

    }
}