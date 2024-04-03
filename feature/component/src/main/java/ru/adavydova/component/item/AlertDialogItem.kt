package ru.adavydova.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.adavydova.catalog_feature.dimensions.Accent


@Composable
fun <T> AlertDialogItem(
    modifier: Modifier = Modifier,
    items: HashMap<T, Boolean>,
    selectedItem: T,
    onClickItem: (T) -> Unit,
    title: String
) {


    var checked by remember {
        mutableStateOf(items[selectedItem] == true)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                text = title
            )

            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Accent
                ),
                checked = checked,
                onCheckedChange = {
                    onClickItem(selectedItem)
                    checked = !checked
                })

        }

        HorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
    }
}
