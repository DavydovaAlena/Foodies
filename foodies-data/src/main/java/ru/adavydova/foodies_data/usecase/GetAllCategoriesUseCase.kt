package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.repository.ProductRepository

class GetAllCategoriesUseCase(
    private val repository: ProductRepository
) {
   suspend  operator fun invoke() = repository.getAllCategories()
}