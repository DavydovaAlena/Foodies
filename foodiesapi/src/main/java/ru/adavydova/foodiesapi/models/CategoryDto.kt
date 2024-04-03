package ru.adavydova.foodiesapi.models

import kotlinx.serialization.SerialName


data class CategoryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
)
