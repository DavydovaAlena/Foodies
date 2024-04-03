package ru.adavydova.basket_feature.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.adavydova.component.basket.BasketLazyColumn
import ru.adavydova.component.catalog.CatalogStateHandle
import ru.adavydova.component.item.BasketItem
import ru.adavydova.component.util.toRuble
import ru.adavydova.foodies_data.models.Basket

@Composable
fun ContentBlock(
    modifier: Modifier = Modifier,
    error: String?,
    load: Boolean,
    onAddClick: (Basket)->Unit,
    onRemoveClick: (Basket)->Unit,
    data: List<Basket>,
    goToDetailScreen: (Long)->Unit
) {

    CatalogStateHandle(
        modifier = modifier
            .fillMaxSize(),
        error = error,
        load = load,
        data = data,
        emptyState = "Корзина пустая",
        errorState = "Произошла ошибка",
        successState = {
            BasketLazyColumn(items = data,
                itemCard = {
                    BasketItem(
                        modifier = Modifier.clickable {
                           goToDetailScreen(it.id)
                        },
                        name = it.name,
                        image = it.image,
                        price = it.priceCurrent.toRuble(),
                        priceOld = it.priceOld?.toRuble(),
                        current = it.numOfPrices,
                        onAddClick = { onAddClick(it) },
                        onRemoveClick = { onRemoveClick(it) })
                })
        })

}