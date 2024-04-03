package ru.adavydova.foodies_data.usecase

data class FoodiesUseCase (
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase,
    val getAllTagsUseCase: GetAllTagsUseCase,
    val getProductsByTagsUseCase: GetProductsByTagsUseCase,
    val getProductsByCategory: GetProductsByCategory,
    val searchProductsByQueryUseCase: SearchProductsByQueryUseCase,
    val addProductToCart: AddProductToCart,
    val removeProductFromCart: RemoveProductFromCart,
    val getAllProductsFromCart: GetAllProductsFromCart,
    val changeNumOfProductsFromCartUseCase: ChangeNumOfProductsFromCartUseCase,
    val addBasketToCardUseCase: AddBasketToCardUseCase,
    val getBasketById: GetBasketById
)