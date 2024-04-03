package ru.adavydova.product_card_feature.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.component.button.ButtonBack
import ru.adavydova.component.button.FixedButton
import ru.adavydova.component.font.BodyTwo
import ru.adavydova.component.item.Characteristics
import ru.adavydova.component.item.ProductCharacteristics
import ru.adavydova.component.util.toRuble
import ru.adavydova.product_card_feature.ProductCardViewModel
import ru.adavydova.product_card_feature.ProductEvent

@Composable
fun DetailScreen(
    onBackPress: () -> Unit,
    viewModel: ProductCardViewModel = hiltViewModel()
) {
    val state by viewModel.productState.collectAsState()

    Scaffold(
        bottomBar = {
            state.product?.let {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(72.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    FixedButton(
                        title = "В корзину за ${it.priceCurrent.toRuble()} ₽",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        viewModel.onEvent(ProductEvent.AddProductToCart)
                    }
                }
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding())
        ) {
            state.product?.let { product ->
                CardProduct(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    image = product.image,
                    title = product.name,
                    description = product.description,
                    categoryBlock = {
                        val characteristics = remember {
                            val data = hashMapOf(
                                "Вес" to "${product.measure} ${product.measureUnit}",
                                "Энерг. ценность" to "${product.energyPerHundredGrams} ккал",
                                "Белки" to "${product.proteinsPerHundredGrams} г",
                                "Жиры" to "${product.fatsPerHundredGrams} г",
                                "Углеводы" to "${product.carbohydratesPerHundredGrams} г"
                            )
                            Characteristics(data)
                        }
                        ProductCharacteristics(
                            modifier = Modifier.fillMaxWidth(),
                            characteristics = characteristics
                        )
                    })
            }

            ButtonBack(
                modifier = Modifier
                    .padding(16.dp)
                    .size(44.dp)
                    .align(Alignment.TopStart)
            ) {
                onBackPress()
            }

            ElevatedCard(
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(16.dp)
                    .height(44.dp)
                    .width(124.dp)
                    .align(Alignment.TopEnd)
            ) {
                Row(modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = ru.adavydova.component.R.drawable.cart),
                        contentDescription = null
                    )
                    state.product?.let {
                        BodyTwo(text = (state.data?.get(it) ?: 0).toString())
                    }
                }
            }
        }


    }


}