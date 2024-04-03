package ru.adavydova.catalog_feature.composable.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.catalog_feature.dimensions.BASIC_FONT_SIZE
import ru.adavydova.catalog_feature.dimensions.GAP
import ru.adavydova.catalog_feature.dimensions.RADIUS
import ru.adavydova.component.item.CategoryItem
import ru.adavydova.foodies_data.models.Category

@Composable
fun Categories(
    modifier: Modifier = Modifier,
    changeCategory: (Category) -> Unit,
    currentCategory: Category?,
    categories: List<Category>
) {

    LazyRow(
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(GAP),
        modifier = modifier
            .background(Color.White)
            .height(40.dp)
    ) {
        items(categories) {
            CategoryItem(
                selected = { changeCategory(it) },
                nameCategory = it.name,
                containerColor = if (currentCategory == it) Accent else Color.White,
                contentColor = if (currentCategory == it) Color.White else Color.Black,
            )
        }
    }
}

