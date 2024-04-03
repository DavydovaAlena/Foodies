package ru.adavydova.foodies_database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "basket")
data class BasketDBO(
    @ColumnInfo(ID)
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    @ColumnInfo(NAME)
    val name: String,
    @ColumnInfo(IMAGE)
    val image: String,
    @ColumnInfo(PRICE_CURRENT)
    val priceCurrent: Int,
    @ColumnInfo(PRICE_OLD)
    val priceOld: Int?,
    @ColumnInfo(MEASURE)
    val measure: Int,
    @ColumnInfo(MEASURE_UNIT)
    val measureUnit: String,
    @ColumnInfo(NUM_OF_PRICES)
    val numOfPrices: Int
){

    companion object {
        const val NUM_OF_PRICES = "NUM_OF_PRICES"
        const val ID = "ID"
        const val NAME = "NAME"
        const val IMAGE = "IMAGE"
        const val PRICE_CURRENT = "PRICE_CURRENT"
        const val PRICE_OLD = "PRICE_OLD"
        const val MEASURE = "MEASURE"
        const val MEASURE_UNIT = "MEASURE_UNIT"
    }
}