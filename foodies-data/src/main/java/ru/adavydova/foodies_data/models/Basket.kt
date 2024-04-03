package ru.adavydova.foodies_data.models

data class Basket (
    val id:Long,
    val name: String,
    val image: String,
    val priceCurrent: Int,
    val priceOld: Int?,
    val measure: Int,
    val measureUnit: String,
    val numOfPrices: Int
)

fun Basket.containsId(idProduct:Long):Boolean{
    return id == idProduct
}