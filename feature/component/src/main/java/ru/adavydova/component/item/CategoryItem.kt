package ru.adavydova.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import ru.adavydova.catalog_feature.dimensions.GAP
import ru.adavydova.catalog_feature.dimensions.RADIUS
import ru.adavydova.component.font.BodyTwo



@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    selected: () -> Unit,
    nameCategory: String
) {
    Box(modifier = modifier
        .clickable { selected() }
        .fillMaxHeight()
        .clip(RoundedCornerShape(RADIUS))
        .background(containerColor)
        .padding(GAP)
        .wrapContentWidth()) {

        BodyTwo(
            modifier = Modifier.align(Alignment.Center),
            color = contentColor,
            text = nameCategory
        )

    }
}