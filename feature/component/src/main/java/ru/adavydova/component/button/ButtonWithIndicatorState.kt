package ru.adavydova.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.component.R

@Composable
fun ButtonWithIndicatorState(
    numOfActiveFilter: Int,
    onClick: () -> Unit,
    badgedContainerColor: Color = Accent,
    @DrawableRes icon: Int = R.drawable.filter
) {
    when (numOfActiveFilter == 0) {
        true -> {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = null
                )
            }
        }
        false -> {
            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier.offset((-10).dp, (5).dp),
                        containerColor = badgedContainerColor
                    ) {
                        Text(
                            color = Color.White,
                            text = "$numOfActiveFilter"
                        )
                    }
                })
            {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = icon),
                        contentDescription = null
                    )
                }
            }
        }

    }
}