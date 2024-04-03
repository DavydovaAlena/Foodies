package ru.adavydova.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.adavydova.catalog_feature.dimensions.BASIC_PADDINGS
import ru.adavydova.catalog_feature.dimensions.GrayBg
import ru.adavydova.component.R
import ru.adavydova.component.button.CounterButton
import ru.adavydova.component.font.BodyOne
import ru.adavydova.component.font.BodyTwo

@Composable
fun BasketItem(
    name: String,
    image: String,
    price: Int,
    priceOld: Int?,
    current: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = BASIC_PADDINGS)
                .padding(horizontal = BASIC_PADDINGS ),
            horizontalArrangement = Arrangement.spacedBy(BASIC_PADDINGS)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .error(R.drawable.image_product)
                    .data(image)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(94.dp)
            )

            DescriptionBasketBlock(
                name = name,
                current = current,
                price = price,
                onAddClick = onAddClick,
                onRemoveClick = onRemoveClick,
                priceOld = priceOld
            )

        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    HorizontalDivider()

}

@Composable
fun DescriptionBasketBlock(
    modifier: Modifier = Modifier,
    name: String,
    current: Int,
    price: Int,
    priceOld: Int?,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom

    ) {

        BodyOne(
            color = Color.Black.copy(alpha = .87f),
            text = name)

        Spacer(modifier = Modifier.height(16.dp))

        CountBasketBlock(
            modifier = Modifier.fillMaxWidth(.5f),
            current = current,
            onAddClick = onAddClick,
            onRemoveClick = onRemoveClick,
            priceOld = priceOld,
            price = price
        )


    }
}


@Composable
fun BasketPriceBlock(
    price: String,
    priceOld: Int?,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        BodyTwo(
            color = Color.Black.copy(alpha = .87f),
            fontSize = 16.sp,
            text = price)

        priceOld?.let {
            BodyOne(
                color = Color.Black.copy(alpha = .60f),
                textDecoration = TextDecoration.LineThrough,
                fontSize = 14.sp,
                text = "$priceOld ₽"
            )
        }
    }
}

@Composable
fun CountBasketBlock(
    current: Int,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    price:Int,
    priceOld:Int?,
    modifier: Modifier = Modifier,

) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {

        CounterButton(
            colorContainer = GrayBg,
            sizeButton = 42.dp,
            modifier = Modifier.width(135.dp),
            numOfSelectItem = current,
            onClickMinus = onRemoveClick,
            onClickPlus = onAddClick
        )

        BasketPriceBlock(
            price = "$price ₽ ",
            priceOld = priceOld )
    }
}