package ru.adavydova.search_screen_feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.adavydova.foodies_data.models.Basket
import ru.adavydova.foodies_data.models.Product
import ru.adavydova.foodies_data.usecase.FoodiesUseCase
import ru.adavydova.foodies_data.utils.Result
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: FoodiesUseCase
) : ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState())
    val searchState = _searchState.asStateFlow()

    private val cacheProduct = MutableStateFlow<List<Basket>>(emptyList())
    private val allCurrentProduct = MutableStateFlow<List<Product>>(emptyList())

    val currentProductState = combine(cacheProduct, allCurrentProduct) { cacheProduct, products ->
        HashMap<Product, Int>().apply {
            products.forEach { product ->
                val search = cacheProduct.find { it.id == product.id }
                when (search) {
                    null -> this[product] = 0
                    else ->  this[product] = search.numOfPrices
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), hashMapOf())



    fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.ClearQuery -> {
                _searchState.update {
                    it.copy(query = "")
                }
            }

            is SearchEvent.UpdateQuery -> {
                _searchState.update {
                    it.copy(query = event.query)
                }
            }

            is SearchEvent.AddProductToCart -> {
                _searchState.update { it.copy(load = !_searchState.value.load) }
                viewModelScope.launch {
                    val result =
                        if (event.current == 0) {
                           useCase.addProductToCart(event.product, 1)
                        } else {
                           useCase.changeNumOfProductsFromCartUseCase(
                                newNum = event.current.plus(1),
                                id = event.product.id
                            )
                        }
                    when (result) {
                        is Result.Error -> {
                            Log.e("CatalogVM", result.error)
                        }

                        is Result.Success -> {
                            onEvent(SearchEvent.GetAllProductsFromCart)
                        }
                    }
                }
                _searchState.update { it.copy(load = !_searchState.value.load) }

            }

            is SearchEvent.RemoveProductFromCart -> {
                _searchState.update { it.copy(load = !_searchState.value.load) }
                viewModelScope.launch {
                    val result =
                        if (event.current == 1) {
                            useCase.removeProductFromCart(event.product.id)
                        } else {
                            Log.d("NUM", event.current.toString())
                            useCase.changeNumOfProductsFromCartUseCase(
                                newNum = event.current.minus(1),
                                id = event.product.id
                            )
                        }
                    when (result) {
                        is Result.Error -> {
                            Log.e("CatalogVM", result.error)
                        }

                        is Result.Success -> {
                            onEvent(SearchEvent.GetAllProductsFromCart)
                        }
                    }
                }
                _searchState.update { it.copy(load = !_searchState.value.load) }

            }
            SearchEvent.GetAllProductsFromCart -> {
                viewModelScope.launch {
                    when (val result = useCase.getAllProductsFromCart()) {
                        is Result.Error -> {
                            Log.e("CatalogVM", result.error)
                        }

                        is Result.Success -> {
                            result.data.collectLatest { productsFromCart ->
                                cacheProduct.update {
                                    productsFromCart
                                }
                            }
                        }
                    }
                }

            }


            SearchEvent.PutRequest -> {
                viewModelScope.launch {
                    when (val result =
                        useCase.searchProductsByQueryUseCase(searchState.value.query)) {
                        is Result.Error -> {
                            onEvent(SearchEvent.ShowErrorScreen(result.error))
                        }

                        is Result.Success -> {
                            result.data.collectLatest { products ->
                                _searchState.update {
                                    it.copy(writeState = false,)
                                }
                                allCurrentProduct.update {
                                    products
                                }
                            }
                        }
                    }
                }

            }

            is SearchEvent.ShowErrorScreen -> {
                _searchState.update {
                    it.copy(error = event.message)
                }
            }
        }
    }


}


sealed class SearchEvent {
    class UpdateQuery(val query: String) : SearchEvent()
    object ClearQuery : SearchEvent()
    object PutRequest : SearchEvent()
    class ShowErrorScreen(val message: String) : SearchEvent()
    data class AddProductToCart(val product: Product, val current: Int) : SearchEvent()
    data object GetAllProductsFromCart : SearchEvent()
    data class RemoveProductFromCart(val product: Product, val current: Int) : SearchEvent()
}

data class SearchState(
    val query: String = "",
    val error: String? = null,
    val load: Boolean = false,
    val writeState: Boolean = true,

)