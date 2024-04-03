package ru.adavydova.foodiesapi.models

import com.google.gson.annotations.SerializedName


data class ProductDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("category_id")
    val categoryId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price_current")
    val priceCurrent: Int,
    @SerializedName("price_old")
    val priceOld: Int?,
    @SerializedName("measure")
    val measure: Int,
    @SerializedName("measure_unit")
    val measureUnit: String,
    @SerializedName("energy_per_100_grams")
    val energyPerHundredGrams: Double,
    @SerializedName("proteins_per_100_grams")
    val proteinsPerHundredGrams: Double,
    @SerializedName("fats_per_100_grams")
    val fatsPerHundredGrams: Double,
    @SerializedName("carbohydrates_per_100_grams")
    val carbohydratesPerHundredGrams: Double,
    @SerializedName("tag_ids")
    val tagIds: List<Int>
)
