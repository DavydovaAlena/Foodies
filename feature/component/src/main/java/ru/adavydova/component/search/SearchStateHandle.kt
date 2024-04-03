package ru.adavydova.component.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import ru.adavydova.component.font.BodyOne

@Composable
fun SearchStateHandle(
    writeState: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {

    val context = LocalContext.current

    when (writeState) {
        true -> {
            SearchWaitingScreen(modifier)
        }

        false -> {
            content(modifier)
        }
    }
}

@Composable
fun SearchWaitingScreen(
    modifier: Modifier = Modifier,
    text: String = "Введите название блюда которое ищите"
) {
    Box(modifier = modifier.fillMaxSize()) {
        BodyOne(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black.copy(alpha = .6f),
            text = text
        )
    }

}