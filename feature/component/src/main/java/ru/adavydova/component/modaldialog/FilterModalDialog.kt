package ru.adavydova.component.modaldialog

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.adavydova.catalog_feature.dimensions.BASIC_PADDINGS
import ru.adavydova.component.button.FixedButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> FilterModalDialog(
    items: HashMap<T, Boolean>,
    currentItem: @Composable (T) ->Unit,
    onApplyClick: () -> Unit,
    onDismiss: () -> Unit,
    showBottomSheet: Boolean,
    modifier: Modifier = Modifier,
    titleModalDialog: String = "Подобрать блюда",
) {


    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = Color.White,
            shape = RoundedCornerShape(24.dp),
            sheetMaxWidth = BottomSheetDefaults.SheetMaxWidth,
            onDismissRequest = {
                onDismiss()
            }) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp)
            ) {

                item {
                    Text(
                        fontStyle = MaterialTheme.typography.headlineSmall.fontStyle,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        text = titleModalDialog
                    )
                    Spacer(modifier = Modifier.height(BASIC_PADDINGS))
                }

                items(items.keys.toList()) {
                    currentItem(it)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    FixedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        onApplyClick()
                    }
                }

            }


        }
    }
}