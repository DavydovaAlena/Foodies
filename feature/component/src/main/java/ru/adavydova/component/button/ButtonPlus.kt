package ru.adavydova.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.catalog_feature.dimensions.RADIUS
import ru.adavydova.component.R


@Composable
fun ButtonPlus(
    onClick: ()->Unit,
    colorContainer: Color = Color.White,
    sizeButton: Dp = 40.dp
){

    ElevatedCard(
        shape = RoundedCornerShape(RADIUS),
        colors = CardDefaults.cardColors(
            containerColor = colorContainer
        ),
        modifier = Modifier
            .clickable { onClick() }
            .size(sizeButton),
    ) {

        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally),
            tint = Accent,
            imageVector = ImageVector.vectorResource(id = R.drawable.plus),
            contentDescription = null
        )

    }

}


@Composable
fun ButtonPlusWithoutColor(
    onClick: ()->Unit
){
    Button(
        colors = ButtonDefaults.buttonColors(
            contentColor = Accent,
            containerColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(RADIUS),
        modifier = Modifier.size(40.dp),
        onClick = onClick) {

        Icon(
            tint = Accent,
            imageVector = ImageVector.vectorResource(id = R.drawable.plus),
            contentDescription =  null)

    }
}

