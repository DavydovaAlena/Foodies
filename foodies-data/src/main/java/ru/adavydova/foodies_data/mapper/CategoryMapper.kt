package ru.adavydova.foodies_data.mapper

import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodiesapi.models.CategoryDto


fun Category.toCategoryDto(): CategoryDto{
    return CategoryDto(
        id = id,
        name = name
    )
}


fun CategoryDto.toCategory(): Category{
    return Category(
        id = id,
        name = name
    )
}