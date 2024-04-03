package ru.adavydova.product_card_feature

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.adavydova.foodies_data.models.Product
import ru.adavydova.foodies_data.usecase.FoodiesUseCase
import ru.adavydova.foodies_data.utils.Result
import javax.inject.Inject

@HiltViewModel
class ProductCardViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val foodiesUseCase: FoodiesUseCase
) : ViewModel() {

    private val _productState = MutableStateFlow<ProductState>(ProductState())
    val productState = _productState.asStateFlow()


    init {
        _productState.update { it.copy(load = true) }
        stateHandle.get<Long>("idProduct")?.let { idCurrentProduct ->
            viewModelScope.launch(Dispatchers.IO) {
                when (val currentProduct = foodiesUseCase.getAllProductsUseCase()) {
                    is Result.Error -> {
                        onEvent(ProductEvent.ErrorScreen(currentProduct.error))
                    }

                    is Result.Success -> {
                        currentProduct.data.collectLatest { products ->
                            val findProduct = products.find { it.id == idCurrentProduct }
                            when (findProduct) {
                                null -> {
                                    onEvent(ProductEvent.ErrorScreen("Product not found"))
                                }

                                else -> {
                                    val cacheProductSearch =
                                        getProductFromCard(id = idCurrentProduct)
                                    when (cacheProductSearch) {
                                        is Result.Error -> {
                                            _productState.update {
                                                it.copy(
                                                    product = findProduct,
                                                    data = hashMapOf(findProduct to 0)
                                                )
                                            }
                                        }

                                        is Result.Success -> {
                                            _productState.update {
                                                it.copy(
                                                    product = findProduct,
                                                    data = hashMapOf(findProduct to cacheProductSearch.data.numOfPrices)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        _productState.update { it.copy(load = false) }
    }


    private suspend fun getProductFromCard(id: Long) = withContext(Dispatchers.IO) {
        foodiesUseCase.getBasketById(id)
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            ProductEvent.AddProductToCart -> {
                viewModelScope.launch {
                    productState.value.product?.let { product: Product ->
                        val currentNum = productState.value.data?.get(product) ?: 0
                        val result = when (currentNum == 0) {
                            true -> foodiesUseCase.addProductToCart(product, 1)
                            false -> foodiesUseCase.changeNumOfProductsFromCartUseCase(
                                currentNum.plus(
                                    1
                                ), id = product.id
                            )
                        }
                        when (result) {
                            is Result.Error -> {
                                Log.e("ProductCardVM", result.error)
                            }

                            is Result.Success -> {
                                when (val updateState = foodiesUseCase.getBasketById(product.id)) {
                                    is Result.Error -> {
                                        Log.e("ProductCardVM", updateState.error)
                                    }

                                    is Result.Success -> {
                                        _productState.update {
                                            it.copy(data = hashMapOf(product to updateState.data.numOfPrices))
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

            is ProductEvent.ErrorScreen -> {
                _productState.update {
                    it.copy(error = event.message)
                }
            }


        }
    }

}

sealed class ProductEvent {
    object AddProductToCart : ProductEvent()
    class ErrorScreen(val message: String) : ProductEvent()
}

data class ProductState(
    val error: String? = null,
    val load: Boolean = false,
    val product: Product? = null,
    val data: HashMap<Product, Int>? = null
)

