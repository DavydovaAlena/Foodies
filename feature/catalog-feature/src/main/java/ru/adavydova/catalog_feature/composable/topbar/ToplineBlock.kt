package ru.adavydova.catalog_feature.composable.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.adavydova.catalog_feature.composable.categories.Categories
import ru.adavydova.catalog_feature.dimensions.GAP
import ru.adavydova.component.topline.TopBarCatalogScreen
import ru.adavydova.foodies_data.models.Category

@Composable
fun ToplineBlock(
    modifier: Modifier = Modifier,
    category: Category?,
    numOfActiveFilters: Int,
    onClickFilterDialog: ()->Unit,
    changeCategory: (Category) -> Unit,
    goToSearchScreen: ()->Unit,
    goOnBasketScreen: () ->Unit,
    categories: List<Category>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(GAP),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        TopBarCatalogScreen(
            numOfActiveFilters = numOfActiveFilters,
            onClickFiltersDialog = onClickFilterDialog,
            goToSearchScreen = goToSearchScreen,
            goToBasketScreen = goOnBasketScreen
        )

        Categories(
            modifier = Modifier.padding(bottom = 16.dp),
            changeCategory = {
                changeCategory(it)
            },
            currentCategory = category,
            categories = categories
        )

    }
}