package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.repository.ProductRepository

class GetAllTagsUseCase(
    private val repository: ProductRepository
) {
   suspend  operator fun invoke() = repository.getAllTags()
}