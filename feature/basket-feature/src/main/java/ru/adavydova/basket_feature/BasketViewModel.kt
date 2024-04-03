package ru.adavydova.basket_feature

import android.util.Log
import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.adavydova.component.util.toRuble
import ru.adavydova.foodies_data.models.Basket
import ru.adavydova.foodies_data.usecase.FoodiesUseCase
import ru.adavydova.foodies_data.utils.Result
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val useCase: FoodiesUseCase
) : ViewModel() {

    private val _basketState = MutableStateFlow<BasketState>(BasketState())
    val basketState = _basketState.asStateFlow()

    private val _data = MutableStateFlow<List<Basket>>(emptyList())
    val data = _data.asStateFlow()

    val currentSum = combine(_data){ baskets->
        baskets.sumOf {
            it.fastSumBy { basket->
                (basket.priceCurrent * basket.numOfPrices).toRuble()}
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)


    init {
        viewModelScope.launch {
            when (val result = useCase.getAllProductsFromCart()) {
                is Result.Error -> {
                    Log.e("BasketVM", result.error)
                }

                is Result.Success -> {
                    result.data.collectLatest { products ->
                        _data.update {
                            products
                        }
                    }

                }
            }
        }

    }

    fun onEvent(event: BasketEvent) {
        when (event) {
            is BasketEvent.AddProductToCart -> {
                viewModelScope.launch {
                    val result =
                        if (event.basket.numOfPrices == 0) {
                            useCase.addBasketToCardUseCase(event.basket)
                        } else {
                            useCase.changeNumOfProductsFromCartUseCase(
                                newNum = event.basket.numOfPrices.plus(1),
                                id = event.basket.id
                            )
                        }
                    when (result) {
                        is Result.Error -> {
                            Log.e("BasketVM", result.error)
                        }

                        is Result.Success -> {
                            onEvent(BasketEvent.GetAllProductsFromCart)
                        }
                    }
                }

            }

            BasketEvent.GetAllProductsFromCart -> {
                viewModelScope.launch {
                    when (val result = useCase.getAllProductsFromCart()) {
                        is Result.Error -> {
                            Log.e("BasketVM", result.error)
                        }

                        is Result.Success -> {
                            result.data.collectLatest { productsFromCart ->
                                _data.update {
                                    productsFromCart
                                }
                            }

                        }
                    }
                }
            }

            is BasketEvent.RemoveProductFromCart -> {
                viewModelScope.launch {
                    val result =
                        if (event.basket.numOfPrices == 1) {
                            useCase.removeProductFromCart(event.basket.id)
                        } else {
                            useCase.changeNumOfProductsFromCartUseCase(
                                newNum = event.basket.numOfPrices.minus(1),
                                id = event.basket.id
                            )
                        }
                    when (result) {
                        is Result.Error -> {
                            Log.e("BasketVM", result.error)
                        }
                        is Result.Success -> {
                            onEvent(BasketEvent.GetAllProductsFromCart)
                        }
                    }
                }

            }

        }
    }


}

sealed class BasketEvent {
    class AddProductToCart(val basket: Basket) : BasketEvent()
    data object GetAllProductsFromCart : BasketEvent()
    class RemoveProductFromCart(val basket: Basket) : BasketEvent()
}

data class BasketState(

    val error: String? = null,
    val load: Boolean = false
)