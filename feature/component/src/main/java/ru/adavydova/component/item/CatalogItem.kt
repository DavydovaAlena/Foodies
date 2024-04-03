package ru.adavydova.component.item

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.adavydova.catalog_feature.dimensions.GAP_BETWEEN_DESCRIPTION_AND_IMAGE
import ru.adavydova.catalog_feature.dimensions.GrayBg
import ru.adavydova.catalog_feature.dimensions.HEIGHT_CATALOG_ITEM
import ru.adavydova.catalog_feature.dimensions.IMAGE_HEIGHT
import ru.adavydova.catalog_feature.dimensions.RADIUS
import ru.adavydova.catalog_feature.dimensions.WIDTH_LIST_ITEM
import ru.adavydova.component.R
import ru.adavydova.component.catalog.DescriptionBlockProductCard


@Composable
fun CatalogItemWithStatus(
    item: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
    sizeIconStatus: Dp = 24.dp,
    oldValue: Int?,
    @DrawableRes status: Int
) {

    Box(modifier = modifier) {
        item(modifier)
        oldValue?.let {
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .size(sizeIconStatus),
                imageVector = ImageVector.vectorResource(id = status),
                contentDescription = null
            )
        }

    }

}


@Composable
fun CatalogItem(
    name: String,
    image: String,
    weight: String,
    price: Int,
    count: Int,
    addItemToCart: () -> Unit,
    onClickAddItem: () -> Unit,
    onClickRemoveItem: () -> Unit,
    modifier: Modifier = Modifier,
    oldPrice: Int? = null
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .clip(RoundedCornerShape(RADIUS))
            .width(WIDTH_LIST_ITEM)
            .background(GrayBg)
            .height(HEIGHT_CATALOG_ITEM)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .error(R.drawable.image_product)
                .data(image)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(IMAGE_HEIGHT)
        )

        when (count == 0) {
            true -> {
                DescriptionBlockProductCard(
                    modifier = Modifier
                        .padding(GAP_BETWEEN_DESCRIPTION_AND_IMAGE),
                    name = name,
                    weight = weight,
                    price = price,
                    oldPrice = oldPrice,
                    addItemToCart = addItemToCart
                )

            }

            false -> {
                DescriptionBlockProductCard(
                    modifier = Modifier
                        .padding(GAP_BETWEEN_DESCRIPTION_AND_IMAGE),
                    name = name,
                    weight = weight,
                    onClickAddItem = onClickAddItem,
                    onClickRemoveItem = onClickRemoveItem,
                    count = count
                )
            }
        }


    }
}

