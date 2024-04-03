package ru.adavydova.foodies_data.mapper

import ru.adavydova.foodies_data.models.Basket
import ru.adavydova.foodies_data.models.Product
import ru.adavydova.foodies_database.models.BasketDBO
import ru.adavydova.foodiesapi.models.ProductDto


fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        categoryId = categoryId,
        name = name,
        description = description,
        image = image,
        priceCurrent = priceCurrent,
        priceOld = priceOld,
        measure = measure,
        measureUnit = measureUnit,
        energyPerHundredGrams = energyPerHundredGrams,
        proteinsPerHundredGrams = proteinsPerHundredGrams,
        fatsPerHundredGrams = fatsPerHundredGrams,
        carbohydratesPerHundredGrams = carbohydratesPerHundredGrams,
        tagIds = tagIds
    )
}
fun Product.toBasketDBO(numOfPrices: Int): BasketDBO {
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
fun Product.toBasket(numOfPrices: Int): Basket {
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


fun Product.toProductDto(): ProductDto {
    return ProductDto(
        id = id,
        categoryId = categoryId,
        name = name,
        description = description,
        image = image,
        priceCurrent = priceCurrent,
        priceOld = priceOld,
        measure = measure,
        measureUnit = measureUnit,
        energyPerHundredGrams = energyPerHundredGrams,
        proteinsPerHundredGrams = proteinsPerHundredGrams,
        fatsPerHundredGrams = fatsPerHundredGrams,
        carbohydratesPerHundredGrams = carbohydratesPerHundredGrams,
        tagIds = tagIds
    )
}