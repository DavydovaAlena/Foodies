package ru.adavydova.search_screen_feature.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.adavydova.catalog_feature.dimensions.BASIC_PADDINGS
import ru.adavydova.component.R
import ru.adavydova.component.catalog.CatalogStateHandle
import ru.adavydova.component.item.CatalogItem
import ru.adavydova.component.item.CatalogItemWithStatus
import ru.adavydova.component.search.SearchBar
import ru.adavydova.component.search.SearchStateHandle
import ru.adavydova.component.util.toRuble
import ru.adavydova.search_screen_feature.SearchEvent
import ru.adavydova.search_screen_feature.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToDetailScreen : (Long)->Unit
) {

    val searchState by viewModel.searchState.collectAsState()
    val data by viewModel.currentProductState.collectAsState()
    val nestedScrollDispatcher = rememberNestedScrollInteropConnection()

    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollDispatcher)
            .fillMaxSize(),
        topBar = {
            SearchBar(
                modifier = Modifier.shadow(elevation = 6.dp, spotColor = Color.Black.copy(alpha = .3f)),
                onValueChange = { viewModel.onEvent(SearchEvent.UpdateQuery(it)) },
                goOnRequest = { viewModel.onEvent(SearchEvent.PutRequest) },
                clearQuery = { viewModel.onEvent(SearchEvent.ClearQuery) },
                query = searchState.query,
                goBack = { goBack() }
            )
        }
    ) { padding ->


        Box(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = BASIC_PADDINGS)
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding()
            ))
        {
            SearchStateHandle(
                modifier = Modifier.fillMaxSize(),
                writeState = searchState.writeState
            ) {
                CatalogStateHandle(
                    modifier =Modifier.fillMaxSize(),
                    error = searchState.error,
                    load = searchState.load,
                    data = data,
                    catalogItem = { item, count ->

                        CatalogItemWithStatus(
                            oldValue = item.priceOld,
                            status = R.drawable.discount,
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
                                        viewModel.onEvent(SearchEvent.AddProductToCart(item, count))
                                    },
                                    count = count,
                                    onClickAddItem = { viewModel.onEvent(SearchEvent.AddProductToCart(item, count))
                                    },
                                    onClickRemoveItem = {
                                        viewModel.onEvent(SearchEvent.RemoveProductFromCart(item, count))
                                    })
                            })

                    }
                )
            }
        }

    }
}