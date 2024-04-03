package ru.adavydova.catalog_feature

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
import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodies_data.models.Product
import ru.adavydova.foodies_data.models.Tag
import ru.adavydova.foodies_data.usecase.FoodiesUseCase
import ru.adavydova.foodies_data.utils.Result
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val foodiesUseCase: FoodiesUseCase
) : ViewModel() {
    private val _catalogState = MutableStateFlow(CatalogScreenState())
    val catalogState = _catalogState.asStateFlow()

    private val cacheProduct = MutableStateFlow<List<Basket>>(emptyList())
    private val allCurrentProduct = MutableStateFlow<List<Product>>(emptyList())

    val currentProductState = combine(cacheProduct, allCurrentProduct) { cacheProduct, products ->
        HashMap<Product, Int>().apply {
            products.forEach { product ->
                val search = cacheProduct.find { it.id == product.id }
                when (search) {
                    null -> this[product] = 0
                    else -> this[product] = search.numOfPrices
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), hashMapOf())


    fun onEvent(event: CatalogEvent) {
        when (event) {

            is CatalogEvent.ShowErrorScreen -> {
                _catalogState.update {
                    it.copy(error = event.message)
                }
            }


            is CatalogEvent.ChangeCategory -> {

                _catalogState.update { it.copy(load = true) }
                viewModelScope.launch {
                    val updateProducts = when (event.tags.isEmpty()) {
                        true -> foodiesUseCase.getProductsByCategory(event.category)
                        false -> foodiesUseCase.getProductsByTagsUseCase(
                            tags = event.tags,
                            category = event.category
                        )
                    }
                    when (updateProducts) {
                        is Result.Error -> {
                            onEvent(CatalogEvent.ShowErrorScreen(updateProducts.error))
                        }

                        is Result.Success -> {
                            updateProducts.data.collectLatest { updtProduct ->
                                allCurrentProduct.update {
                                    updtProduct
                                }
                            }
                        }
                    }
                }
                _catalogState.update { it.copy(load = false) }
            }

            is CatalogEvent.AddProductToCart -> {
                _catalogState.update { it.copy(load = true) }
                viewModelScope.launch {
                    val result =
                        if (event.current == 0) {
                            foodiesUseCase.addProductToCart(event.product, 1)
                        } else {
                            foodiesUseCase.changeNumOfProductsFromCartUseCase(
                                newNum = event.current.plus(1),
                                id = event.product.id
                            )
                        }
                    when (result) {
                        is Result.Error -> {
                            Log.e("CatalogVM", result.error)
                        }

                        is Result.Success -> {
                            onEvent(CatalogEvent.GetAllProductsFromCart)
                        }
                    }
                }

            }

            CatalogEvent.GetAllProductsFromCart -> {
                viewModelScope.launch {
                    when (val result = foodiesUseCase.getAllProductsFromCart()) {
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
                _catalogState.update { it.copy(load = false) }
            }

            is CatalogEvent.RemoveProductFromCart -> {
                _catalogState.update { it.copy(load = true) }
                viewModelScope.launch {
                    val result =
                        if (event.current == 1) {
                            foodiesUseCase.removeProductFromCart(event.product.id)
                        } else {
                            Log.d("NUM", event.current.toString())
                            foodiesUseCase.changeNumOfProductsFromCartUseCase(
                                newNum = event.current.minus(1),
                                id = event.product.id
                            )
                        }
                    when (result) {
                        is Result.Error -> {
                            Log.e("CatalogVM", result.error)
                        }

                        is Result.Success -> {
                            onEvent(CatalogEvent.GetAllProductsFromCart)
                        }
                    }
                }

            }
        }
    }

}


sealed class CatalogEvent {
    class ShowErrorScreen(val message: String) : CatalogEvent()
    data class ChangeCategory(val category: Category, val tags: List<Tag>) : CatalogEvent()
    data class AddProductToCart(val product: Product, val current: Int) : CatalogEvent()
    data object GetAllProductsFromCart : CatalogEvent()
    data class RemoveProductFromCart(val product: Product, val current: Int) : CatalogEvent()

}

data class CatalogScreenState(
    val error: String? = null,
    val load: Boolean = true
)