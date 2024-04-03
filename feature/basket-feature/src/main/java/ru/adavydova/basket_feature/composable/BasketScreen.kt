package ru.adavydova.basket_feature.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.adavydova.basket_feature.BasketEvent
import ru.adavydova.basket_feature.BasketViewModel
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.component.button.FixedButton
import ru.adavydova.component.font.BodyTwo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    viewModel: BasketViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    goToDetailScreen: (Long) -> Unit
) {
    val state by viewModel.basketState.collectAsState()
    val data by viewModel.data.collectAsState()
    val current by viewModel.currentSum.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .clickable { onBackClick() }
                            .padding(16.dp),
                        imageVector = ImageVector.vectorResource(id = ru.adavydova.component.R.drawable.arrowleft),
                        contentDescription = null,
                        tint = Accent
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black.copy(alpha = .87f)
                ),
                title = {
                    BodyTwo(
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp,
                        text = "Корзина"
                    )
                }
            )
        },
        bottomBar = {

            Box(
                modifier = Modifier
                    .shadow(elevation = 1.dp, ambientColor = Color.LightGray)
                    .fillMaxWidth()
                    .height(72.dp)
            ) {
                FixedButton(
                    colorContent = Color.White,
                    title = "$current ₽",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                ) {

                }
            }


        }) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
        ) {
            ContentBlock(
                modifier = Modifier
                    .fillMaxSize(),
                error = state.error,
                load = state.load,
                data = data,
                onRemoveClick = {
                    viewModel.onEvent(BasketEvent.RemoveProductFromCart(it))
                },
                onAddClick = {
                    viewModel.onEvent(BasketEvent.AddProductToCart(it))
                },
                goToDetailScreen = goToDetailScreen
            )
        }
    }
}