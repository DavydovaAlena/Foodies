package ru.adavydova.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.adavydova.component.font.BodyOne


data class Characteristics(
    val data: HashMap<String,String>
)

@Composable
fun ProductCharacteristics(
    modifier: Modifier = Modifier,
    characteristics: Characteristics
){
    Column(modifier = modifier) {
        HorizontalDivider()
        (characteristics.data.keys.toList()).forEach{
            ProductCharacteristicItem(
                title = it,
                description = characteristics.data[it]!!
            )
        }
    }
}

@Composable
fun ProductCharacteristicItem(
    title:String,
    description:String,
    modifier: Modifier = Modifier
) {


    Column(modifier = modifier
        .height(50.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceAround) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){

            BodyOne(
                color = Color.Black.copy(alpha = .60f),
                text = title)

            BodyOne(text = description)
        }

        HorizontalDivider()
    }
}