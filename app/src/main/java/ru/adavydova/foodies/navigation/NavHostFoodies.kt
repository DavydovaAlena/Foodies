package ru.adavydova.foodies.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import ru.adavydova.basket_feature.composable.BasketScreen
import ru.adavydova.catalog_feature.composable.CatalogScreen
import ru.adavydova.foodies.R
import ru.adavydova.product_card_feature.composable.DetailScreen
import ru.adavydova.search_screen_feature.composable.SearchScreen

@Composable
fun NavHostFoodies(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.FoodiesNavGraph.route
    ) {
        foodiesNavGraph(navController)
    }
}

fun NavController.goToDetailScreen(id: Long) {
    navigate(Route.DetailProductScreen.route + "?idProduct=$id")
}

fun NavGraphBuilder.foodiesNavGraph(navController: NavController) {
    navigation(
        startDestination = Route.SplashScreen.route,
        route = Route.FoodiesNavGraph.route
    ) {

        composable(route = Route.SplashScreen.route){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0XFFF15412)),
            ) {

                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(
                        R.raw.splash
                    )
                )
                val logoAnimationState = animateLottieCompositionAsState(composition = composition)
                LottieAnimation(
                    composition = composition,
                    progress = { logoAnimationState.progress })

                if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying){
                    navController.navigate(Route.CatalogScreen.route){
                        popUpTo(Route.SplashScreen.route){
                            inclusive = true
                        }
                    }
                }

            }
        }


        composable(route = Route.CatalogScreen.route) {
            CatalogScreen(
                goToSearchScreen = {
                    navController.navigate(Route.SearchScreen.route)
                },
                goOnBasketScreen = {
                    navController.navigate(Route.BasketScreen.route)
                },
                goToDetailScreen = {
                    navController.goToDetailScreen(it)
                }
            )
        }

        composable(route = Route.SearchScreen.route) {
            SearchScreen(
                goBack = { navController.popBackStack() },
                goToDetailScreen = {
                    navController.goToDetailScreen(it)
                })
        }

        composable(route = Route.BasketScreen.route) {
            BasketScreen(onBackClick = {
                navController.popBackStack()
            },
                goToDetailScreen = {
                    navController.goToDetailScreen(it)
                }
            )
        }

        composable(
            route = Route.DetailProductScreen.route + "?idProduct={idProduct}",
            arguments = listOf(
                navArgument(
                    name = "idProduct",
                    builder = {
                        type = NavType.LongType
                    }
                )
            )
        ) {
            DetailScreen(onBackPress = {
                navController.popBackStack()
            })
        }

    }
}