package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodies_data.models.Tag
import ru.adavydova.foodies_data.repository.ProductRepository

class GetProductsByCategory (
    private val repository: ProductRepository
) {
    suspend  operator fun invoke(category: Category) = repository.getProductsByCategory(category)
}