package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.mapper.toProduct
import ru.adavydova.foodies_data.models.Product
import ru.adavydova.foodies_data.repository.ProductRepository
import ru.adavydova.foodiesapi.models.ProductDto

class AddProductToCart (
    private val repository: ProductRepository
) {
    suspend operator fun invoke (product: Product, numOfPrices: Int ) =
        repository.addProductToCart(product, numOfPrices)

}

