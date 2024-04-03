package ru.adavydova.component.topline

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.catalog_feature.dimensions.TOPLINE_HEIGHT
import ru.adavydova.catalog_feature.dimensions.BASIC_PADDINGS
import ru.adavydova.component.R
import ru.adavydova.component.button.ButtonWithIndicatorState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCatalogScreen(
    onClickFiltersDialog: () -> Unit,
    goToSearchScreen: ()->Unit,
    numOfActiveFilters: Int = 0,
    goToBasketScreen: ()->Unit,
    badgedContainerColor: Color = Accent,
    badgedContentColor: Color = Color.White
) {

    var stateFilters by remember {
        mutableStateOf<Boolean>(false)
    }
    LaunchedEffect(key1 = numOfActiveFilters) {
        stateFilters = numOfActiveFilters > 0
    }

    TopAppBar(modifier = Modifier
        .padding(top = BASIC_PADDINGS)
        .fillMaxWidth()
        .height(TOPLINE_HEIGHT),

        title = {
            Icon(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(120.dp)
                    .padding(start = 36.dp),
                tint = Accent,
                imageVector = ImageVector.vectorResource(id = R.drawable.logo),
                contentDescription = null
            )
        },
        navigationIcon = {
            ButtonWithIndicatorState(
                numOfActiveFilter = numOfActiveFilters,
                onClick = onClickFiltersDialog)
        },
        actions = {
            IconButton(onClick = goToSearchScreen) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.search),
                    contentDescription = null
                )
            }

            IconButton(onClick = goToBasketScreen) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.cart),
                    contentDescription = null
                )
            }
        }
    )
}