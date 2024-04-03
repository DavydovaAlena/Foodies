package ru.adavydova.foodies_data.models


data class Product(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val description: String,
    val image: String,
    val priceCurrent: Int,
    val priceOld: Int?,
    val measure: Int,
    val measureUnit: String,
    val energyPerHundredGrams: Double,
    val proteinsPerHundredGrams: Double,
    val fatsPerHundredGrams: Double,
    val carbohydratesPerHundredGrams: Double,
    val tagIds: List<Int>
)