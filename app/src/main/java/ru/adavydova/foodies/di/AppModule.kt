package ru.adavydova.foodies.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.adavydova.foodies_data.repository.ProductRepository
import ru.adavydova.foodies_data.repository.ProductRepositoryImpl
import ru.adavydova.foodies_data.usecase.AddBasketToCardUseCase
import ru.adavydova.foodies_data.usecase.AddProductToCart
import ru.adavydova.foodies_data.usecase.ChangeNumOfProductsFromCartUseCase
import ru.adavydova.foodies_data.usecase.FoodiesUseCase
import ru.adavydova.foodies_data.usecase.GetAllCategoriesUseCase
import ru.adavydova.foodies_data.usecase.GetAllProductsFromCart
import ru.adavydova.foodies_data.usecase.GetAllProductsUseCase
import ru.adavydova.foodies_data.usecase.GetAllTagsUseCase
import ru.adavydova.foodies_data.usecase.GetBasketById
import ru.adavydova.foodies_data.usecase.GetProductsByCategory
import ru.adavydova.foodies_data.usecase.GetProductsByTagsUseCase
import ru.adavydova.foodies_data.usecase.RemoveProductFromCart
import ru.adavydova.foodies_data.usecase.SearchProductsByQueryUseCase
import ru.adavydova.foodies_database.FoodiesDatabase
import ru.adavydova.foodies_database.dao.BasketDao
import ru.adavydova.foodiesapi.FoodiesApi
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun foodiesApi(): FoodiesApi {
        return Retrofit.Builder()
            .baseUrl("https://anika1d.github.io/WorkTestServer/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
            .create(FoodiesApi::class.java)

    }

    @Singleton
    @Provides
    fun provideDatabase(context: Application): FoodiesDatabase {
        return Room.inMemoryDatabaseBuilder(
            context, FoodiesDatabase::class.java).build()
    }

    @Singleton
    @Provides
    fun provideBasketDao(foodiesDatabase: FoodiesDatabase): BasketDao{
        return foodiesDatabase.basketDao
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        foodiesApi: FoodiesApi, dao: BasketDao): ProductRepository {
        return ProductRepositoryImpl(foodiesApi, dao)
    }

    @Singleton
    @Provides
    fun provideFoodiesUseCase(
        productRepository: ProductRepository
    ): FoodiesUseCase {
        return FoodiesUseCase(
            getAllCategoriesUseCase = GetAllCategoriesUseCase(productRepository),
            getAllProductsUseCase = GetAllProductsUseCase(productRepository),
            getAllTagsUseCase = GetAllTagsUseCase(productRepository),
            getProductsByCategory = GetProductsByCategory(productRepository),
            getProductsByTagsUseCase = GetProductsByTagsUseCase(productRepository),
            searchProductsByQueryUseCase = SearchProductsByQueryUseCase(productRepository),
            getAllProductsFromCart = GetAllProductsFromCart(productRepository),
            addProductToCart = AddProductToCart(productRepository),
            removeProductFromCart = RemoveProductFromCart(productRepository),
            changeNumOfProductsFromCartUseCase = ChangeNumOfProductsFromCartUseCase(productRepository),
            addBasketToCardUseCase = AddBasketToCardUseCase(productRepository),
            getBasketById = GetBasketById(productRepository)
        )
    }

}