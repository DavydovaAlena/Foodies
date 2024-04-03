package ru.adavydova.catalog_feature.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.adavydova.catalog_feature.ToplineEvent
import ru.adavydova.catalog_feature.ToplineViewModel
import ru.adavydova.catalog_feature.composable.catalog_block.CatalogBlock
import ru.adavydova.catalog_feature.composable.topbar.ToplineBlock
import ru.adavydova.catalog_feature.dimensions.BASIC_PADDINGS
import ru.adavydova.component.item.AlertDialogItem
import ru.adavydova.component.modaldialog.FilterModalDialog

@Composable
fun CatalogScreen(
    viewModel: ToplineViewModel = hiltViewModel(),
    goOnBasketScreen: () ->Unit,
    goToSearchScreen: () -> Unit,
    goToDetailScreen : (Long)->Unit

) {

    val state by viewModel.toplineState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            ToplineBlock(
                modifier = Modifier.fillMaxWidth(),
                category = state.currentCategory,
                categories = state.categories,
                changeCategory = { viewModel.onEvent(ToplineEvent.SelectCategory(it)) },
                numOfActiveFilters = state.activeFilters.filter { (tag, value) -> value }.size,
                onClickFilterDialog = { viewModel.onEvent(ToplineEvent.OpenCloseFilterMenu) },
                goToSearchScreen = goToSearchScreen,
                goOnBasketScreen = goOnBasketScreen
            )
        }) {

        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = BASIC_PADDINGS,
                end = BASIC_PADDINGS
            )
        ) {

            CatalogBlock(
                modifier = Modifier.fillMaxSize(),
                currentCategory = state.currentCategory,
                stateFilterDialog = state.filterDialogStatus,
                currentFilters = state.activeFilters.filter { (tag, value) -> value }.keys.toList(),
                goToDetailScreen = goToDetailScreen
            )

            FilterModalDialog(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 24.dp)
                    .fillMaxWidth(),
                items = state.activeFilters,
                currentItem = { tag ->
                    AlertDialogItem(
                        items = state.activeFilters,
                        selectedItem = tag,
                        onClickItem = { item ->
                            viewModel.onEvent(ToplineEvent.SelectTag(item))
                        },
                        title = tag.name
                    )
                },
                onApplyClick = {
                    viewModel.onEvent(ToplineEvent.ApplyTagAndCloseMenu)
                },
                onDismiss = {
                    viewModel.onEvent(ToplineEvent.OpenCloseFilterMenu)
                },
                showBottomSheet = state.filterDialogStatus
            )

        }

    }
}