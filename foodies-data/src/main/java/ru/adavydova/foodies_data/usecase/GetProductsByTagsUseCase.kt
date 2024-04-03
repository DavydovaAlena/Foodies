package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodies_data.models.Tag
import ru.adavydova.foodies_data.repository.ProductRepository

class GetProductsByTagsUseCase(
    private val repository: ProductRepository
) {
    suspend  operator fun invoke(tags: List<Tag>, category: Category) = repository.getProductsByTags(tags, category)
}