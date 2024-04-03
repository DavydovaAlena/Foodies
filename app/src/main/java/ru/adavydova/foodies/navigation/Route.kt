package ru.adavydova.foodies.navigation

sealed class Route(val route: String){

    object SearchScreen: Route(route= "search_screen_route")
    object DetailProductScreen: Route(route = "detail_product_screen_route")
    object SplashScreen: Route(route = "splash_screen_route")
    object BasketScreen : Route(route = "basket_screen")
    object CatalogScreen : Route(route = "catalog_screen")
    object FoodiesNavGraph : Route(route = "foodies_nav_graph")

}