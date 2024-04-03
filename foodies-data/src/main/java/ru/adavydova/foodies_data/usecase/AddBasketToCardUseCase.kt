package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.models.Basket
import ru.adavydova.foodies_data.repository.ProductRepository

class AddBasketToCardUseCase (
    val repository: ProductRepository
) {
    suspend operator fun invoke(basket: Basket) = repository.addBasketToCart(basket)
}