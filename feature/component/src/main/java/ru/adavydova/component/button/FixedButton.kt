package ru.adavydova.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.catalog_feature.dimensions.FIXED_BUTTON_HEIGHT
import ru.adavydova.catalog_feature.dimensions.RADIUS
import ru.adavydova.component.font.BodyTwo

@Composable
fun FixedButton(
    modifier: Modifier = Modifier,
    title: String = "Готово",
    colorContainer: Color = Accent,
    colorContent: Color = Color.White,
    apply: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(RADIUS),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorContainer,
            contentColor = colorContent
        ),
        modifier = modifier
            .clickable { apply() }
            .fillMaxWidth()
            .height(FIXED_BUTTON_HEIGHT),
        onClick = { apply() }) {

        BodyTwo(
            color = colorContent,
            text = title)
    }
}
