package ru.adavydova.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import ru.adavydova.component.R

@Composable
fun ButtonBack(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White,
    contentColor: Color = Color(0xff333333),
    onClick: () -> Unit
) {

    ElevatedCard(
        modifier = modifier,
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            contentColor = contentColor,
            containerColor = containerColor
        ),
        onClick = onClick
    ) {

        Icon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxHeight(),
            imageVector = ImageVector.vectorResource(id = R.drawable.arrowleft),
            contentDescription = null
        )
    }
}