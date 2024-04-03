package ru.adavydova.catalog_feature.composable.catalog_block

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ru.adavydova.catalog_feature.CatalogEvent
import ru.adavydova.catalog_feature.CatalogViewModel
import ru.adavydova.component.catalog.CatalogStateHandle
import ru.adavydova.component.item.CatalogItem
import ru.adavydova.component.item.CatalogItemWithStatus
import ru.adavydova.component.util.toRuble
import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodies_data.models.Tag

@Composable
fun CatalogBlock(
    modifier: Modifier,
    currentCategory: Category?,
    currentFilters: List<Tag>,
    goToDetailScreen: (Long) -> Unit,
    stateFilterDialog: Boolean,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val catalogState by viewModel.catalogState.collectAsState()
    val data by viewModel.currentProductState.collectAsState()

    val filters = remember { currentFilters.toMutableStateList() }


    LaunchedEffect(key1 = currentCategory?.id) {
        if (currentCategory != null) {
            viewModel.onEvent(CatalogEvent.ChangeCategory(currentCategory, currentFilters))
        }
    }

    LaunchedEffect(key1 = stateFilterDialog) {

        if (!stateFilterDialog && currentFilters != filters.toList()) {
            currentCategory?.let {
                viewModel.onEvent(CatalogEvent.ChangeCategory(currentCategory, currentFilters))
                filters.clear()
                filters.addAll(currentFilters)
            }
        }
    }

    CatalogStateHandle(
        modifier = modifier.fillMaxSize(),
        error = catalogState.error,
        load = catalogState.load,
        data = data,
        catalogItem = { item, count ->

            CatalogItemWithStatus(
                oldValue = item.priceOld,
                status = ru.adavydova.component.R.drawable.discount,
                item = {
                    CatalogItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                goToDetailScreen(item.id)
                            },
                        name = item.name,
                        image = item.image,
                        oldPrice = item.priceOld?.toRuble(),
                        weight = "${item.measure} ${item.measureUnit}",
                        price = item.priceCurrent.toRuble(),
                        addItemToCart = {
                            viewModel.onEvent(CatalogEvent.AddProductToCart(item, count))
                        },
                        count = count,
                        onClickAddItem = { viewModel.onEvent(CatalogEvent.AddProductToCart(item, count))
                        },
                        onClickRemoveItem = {
                            viewModel.onEvent(CatalogEvent.RemoveProductFromCart(item, count))
                        })
                })

        }

    )
}

