package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.repository.ProductRepository

class RemoveProductFromCart (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id:Long) = repository.removeProductFromCart(id)
}