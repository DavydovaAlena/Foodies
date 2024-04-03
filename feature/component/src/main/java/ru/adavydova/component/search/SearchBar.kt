package ru.adavydova.component.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.adavydova.catalog_feature.dimensions.Accent
import ru.adavydova.component.R


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    goOnRequest: (String) -> Unit,
    goBack: () -> Unit,
    clearQuery: () -> Unit,
    query: String,
) {


    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    var clearState by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = query, block = { clearState = query.isNotEmpty() })

    var queryTextField by remember {
        mutableStateOf(
            TextFieldValue(
                text = query,
                selection = when (query.isEmpty()) {
                    true -> TextRange.Zero
                    false -> TextRange(query.length)
                }
            )
        )
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = queryTextField,
        maxLines = 2,
        onValueChange = {
            queryTextField = it
            onValueChange(it.text)
        },

        placeholder = {
            Text(
                fontWeight = FontWeight.W400,
                color = Color.Black.copy(alpha = .6f),
                text = "Найти блюдо"
            )
        },

        leadingIcon = {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = Accent
                ),
                onClick = goBack
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrowleft),
                    contentDescription = null
                )
            }
        },

        trailingIcon = {
            if (clearState) {
                IconButton(onClick = {
                    clearQuery()
                    queryTextField = TextFieldValue(text = "", selection = TextRange.Zero)
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.cancel),
                        contentDescription = null,
                        tint = Color.Black.copy(alpha = .60f)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                goOnRequest(query)
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.colorSearchBar
    )


}

val TextFieldDefaults.colorSearchBar: TextFieldColors
    @Composable
    get() =
        TextFieldDefaults.colors(
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.outline,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledIndicatorColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,

            )