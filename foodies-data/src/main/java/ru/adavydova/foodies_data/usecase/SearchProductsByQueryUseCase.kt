package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodies_data.models.Tag
import ru.adavydova.foodies_data.repository.ProductRepository

class SearchProductsByQueryUseCase (
    private val repository: ProductRepository
) {
    suspend  operator fun invoke(query:String) = repository.searchProducts(query)
}