package ru.adavydova.foodies_data.usecase

import ru.adavydova.foodies_data.repository.ProductRepository

class ChangeNumOfProductsFromCartUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(newNum: Int, id:Long) =
        repository.changeNumOfProductFromCart(newNum, id)
}