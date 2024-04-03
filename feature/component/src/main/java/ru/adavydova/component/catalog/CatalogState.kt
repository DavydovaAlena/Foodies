package ru.adavydova.component.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.adavydova.catalog_feature.dimensions.GAP


@Composable
fun CatalogErrorState(
    modifier: Modifier = Modifier,
    error: String
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            text = error
        )
    }
}

@Composable
fun CatalogEmptyState(
    modifier: Modifier = Modifier,
    text: String = "Таких блюд нет :("
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            text = text
        )
    }
}

@Composable
fun CatalogLoadState(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            text = "Загрузка"
        )
    }
}




@Composable
fun <T> CatalogSuccessState(
    modifier: Modifier = Modifier,
    data: HashMap<T, Int>,
    productCard: @Composable (T,Int) -> Unit,
    lazyGridState: LazyGridState = rememberLazyGridState()
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(GAP),
        verticalArrangement = Arrangement.spacedBy(GAP)
    ) {

        itemsIndexed(data.keys.toList()) {index, item->
            productCard(item, data.getOrDefault(item, 0))
        }
    }

}

@Composable
fun <T> CatalogSuccessState(
    modifier: Modifier = Modifier,
    data: List<T>,
    productCard: @Composable (T) -> Unit,
    lazyGridState: LazyGridState = rememberLazyGridState()
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState,
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(GAP),
        verticalArrangement = Arrangement.spacedBy(GAP)
    ) {

        itemsIndexed(data) {index, item->
            productCard(item)
        }
    }

}
