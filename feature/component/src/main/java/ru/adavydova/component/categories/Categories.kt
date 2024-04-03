package ru.adavydova.component.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.catalog_feature.dimensions.GAP
import ru.adavydova.catalog_feature.dimensions.HEIGHT_CATEGORY
import ru.adavydova.catalog_feature.dimensions.BASIC_PADDINGS
import ru.adavydova.component.item.CategoryItem


@Composable
fun <T> Categories(
    categoryItem: @Composable (T)->Unit,
    categories: List<T>,
    modifier: Modifier = Modifier
) {

    LazyRow(
        contentPadding = PaddingValues(start = BASIC_PADDINGS),
        horizontalArrangement = Arrangement.spacedBy(GAP),
        modifier = modifier
            .background(Color.White)
            .height(HEIGHT_CATEGORY)
    ) {
        items(categories) {
            categoryItem(it)
        }
    }
}


