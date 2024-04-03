package ru.adavydova.component.basket

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun <T> BasketLazyColumn(
    items: List<T>,
    itemCard: @Composable (T)->Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier){
        items(items){
            itemCard(it)
        }
    }

}