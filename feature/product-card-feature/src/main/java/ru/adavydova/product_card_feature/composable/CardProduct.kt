package ru.adavydova.product_card_feature.composable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.adavydova.component.R
import ru.adavydova.component.font.BodyOne

@Composable
fun CardProduct(
    image: String,
    title: String,
    description: String,
    categoryBlock: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState()
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                scroll,

            )
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .error(R.drawable.image_product)
                .data(image)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(320.dp)
        )


        ProductDescriptionBlock(
            title = title,
            description = description
        )
        categoryBlock()


    }
}

@Composable
fun ProductDescriptionBlock(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {

        BodyOne(
            lineHeight = 32.sp,
            fontSize = 28.sp,
            text = title
        )

        BodyOne(
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            color = Color.Black.copy(alpha = .6f),
            text = description
        )
    }
}