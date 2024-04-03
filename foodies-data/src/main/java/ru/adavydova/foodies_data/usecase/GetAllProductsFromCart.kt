package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.repository.ProductRepository

class GetAllProductsFromCart (
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() = productRepository.getAllProductsFromCart()
}