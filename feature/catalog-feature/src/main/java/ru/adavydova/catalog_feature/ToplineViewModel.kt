package ru.adavydova.catalog_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodies_data.models.Tag
import ru.adavydova.foodies_data.usecase.FoodiesUseCase
import ru.adavydova.foodies_data.utils.Result
import javax.inject.Inject

@HiltViewModel
class ToplineViewModel @Inject constructor(
    private val foodiesUseCase: FoodiesUseCase
) : ViewModel() {

    private val _toplineState = MutableStateFlow<ToplineState>(ToplineState())
    val toplineState = _toplineState.asStateFlow()

    init {
        viewModelScope.launch {
            getCategories()
        }
        viewModelScope.launch {
            getTags()
        }
    }

    private suspend fun getTags() {
        when (val tags = foodiesUseCase.getAllTagsUseCase()) {
            is Result.Error -> {
                Log.e("ToplineVM", tags.error)
            }

            is Result.Success -> {
                tags.data.collectLatest {
                    _toplineState.value = _toplineState.value.copy(
                        activeFilters = it,
                    )
                }
            }
        }
    }

    private suspend fun getCategories() {
        when (val categories = foodiesUseCase.getAllCategoriesUseCase()) {
            is Result.Error -> {
                Log.e("ToplineVM", categories.error)
            }

            is Result.Success -> {
                categories.data.collectLatest {
                    _toplineState.value = _toplineState.value.copy(
                        categories = it,
                        currentCategory = it.first()
                    )
                }
            }
        }
    }

    fun onEvent(event: ToplineEvent) {
        when (event) {

            is ToplineEvent.ApplyTagAndCloseMenu -> {
                viewModelScope.launch {
                    _toplineState.value = _toplineState.value.copy(
                        filterDialogStatus = !_toplineState.value.filterDialogStatus
                    )
                }

            }

            ToplineEvent.OpenCloseFilterMenu -> {
                _toplineState.value = _toplineState.value.copy(
                    filterDialogStatus = !_toplineState.value.filterDialogStatus
                )
            }

            is ToplineEvent.SelectCategory -> {
                _toplineState.value = _toplineState.value.copy(
                    currentCategory = event.category
                )
            }

            is ToplineEvent.SelectTag -> {
                val newState = _toplineState.value.activeFilters
                newState[event.tag] = newState[event.tag]?.let { !it } ?: false
                _toplineState.update {
                    it.copy(
                        activeFilters = newState
                    )
                }
            }
        }
    }
}


sealed class ToplineEvent {
    object OpenCloseFilterMenu : ToplineEvent()
    class SelectCategory(val category: Category) : ToplineEvent()
    object ApplyTagAndCloseMenu : ToplineEvent()
    class SelectTag(val tag: Tag) : ToplineEvent()
}

data class ToplineState(
    val categories: List<Category> = emptyList(),
    val filterDialogStatus: Boolean = false,
    val currentCategory: Category? = null,
    val activeFilters: HashMap<Tag, Boolean> = hashMapOf<Tag, Boolean>(),
)