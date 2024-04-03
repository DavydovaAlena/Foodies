package ru.adavydova.foodies_data.mapper

import ru.adavydova.foodies_data.models.Tag
import ru.adavydova.foodiesapi.models.TagDto


fun Tag.toTagDto(): TagDto {
    return TagDto(
        id = id,
        name = name
    )
}

fun TagDto.toTag(): Tag {
    return Tag(
        id = id,
        name = name
    )
}