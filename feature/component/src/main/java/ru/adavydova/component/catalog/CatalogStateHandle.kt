package ru.adavydova.component.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> CatalogStateHandle(
    error: String?,
    load: Boolean,
    data: HashMap<T, Int>,
    catalogItem: @Composable (T, Int) -> Unit,
    modifier: Modifier = Modifier,
    errorState: @Composable () -> Unit = {
        CatalogErrorState(
            modifier = modifier,
            error = error ?: ""
        )
    },
    loadState: @Composable () -> Unit = {
        CatalogLoadState(modifier = modifier)
    },
    emptyState: @Composable () -> Unit = {
        CatalogEmptyState(modifier = modifier)
    },
    successState: @Composable () -> Unit = {
        CatalogSuccessState(
            data = data, productCard = { item, coun ->
                catalogItem(item, coun)
            })
    }
) {
    when (error) {
        null -> {
            when (load) {
                true -> {
                    loadState()
                }

                false -> {
                    if (data.isEmpty()) {
                        emptyState()
                    } else {
                        successState()
                    }
                }
            }
        }

        else -> {
            errorState()
        }
    }
}


@Composable
fun <T> CatalogStateHandle(
    error: String?,
    load: Boolean,
    data: List<T>,
    errorState: String,
    emptyState: String,
    successState: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    loadState: @Composable () -> Unit = { CatalogLoadState(modifier = modifier) }

) {
    when (error) {
        null -> {
            when (load) {
                true -> {
                    loadState()
                }
                false -> {
                    if (data.isEmpty()) {
                        CatalogEmptyState(
                            text = emptyState,
                            modifier = modifier)
                    } else {
                        successState()
                    }
                }
            }
        }

        else -> {
            CatalogErrorState(
                modifier = modifier,
                error = error ?: ""
            )
        }
    }
}

@Composable
fun <T> CatalogStateHandle(
    error: String?,
    load: Boolean,
    data: List<T>,
    modifier: Modifier = Modifier,
    errorState: @Composable () -> Unit = {
        CatalogErrorState(
            modifier = modifier,
            error = error ?: ""
        )
    },
    loadState: @Composable () -> Unit = {
        CatalogLoadState(modifier = modifier)
    },
    emptyState: @Composable () -> Unit = {
        CatalogEmptyState(modifier = modifier)
    },
    successState: @Composable () -> Unit

) {
    when (error) {
        null -> {
            when (load) {
                true -> {
                    loadState()
                }

                false -> {
                    if (data.isEmpty()) {
                        emptyState()
                    } else {
                        successState()
                    }
                }
            }
        }

        else -> {
            errorState()
        }
    }
}