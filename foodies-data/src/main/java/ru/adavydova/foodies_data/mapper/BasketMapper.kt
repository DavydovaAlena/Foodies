package ru.adavydova.foodies_data.mapper

import ru.adavydova.foodies_data.models.Basket
import ru.adavydova.foodies_database.models.BasketDBO


fun BasketDBO.toBasket(): Basket {
    return Basket(
        id = id,
        name = name,
        image = image,
        priceCurrent = priceCurrent,
        priceOld = priceOld,
        measure = measure,
        measureUnit = measureUnit,
        numOfPrices = numOfPrices
    )
}

fun Basket.toBasketDBO(): BasketDBO{
    return BasketDBO(
        id = id,
        name = name,
        image = image,
        priceCurrent = priceCurrent,
        priceOld = priceOld,
        measure = measure,
        measureUnit = measureUnit,
        numOfPrices = numOfPrices
    )
}