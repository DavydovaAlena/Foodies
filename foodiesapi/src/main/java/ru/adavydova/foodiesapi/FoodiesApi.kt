package ru.adavydova.foodiesapi

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import ru.adavydova.foodiesapi.models.CategoryDto
import ru.adavydova.foodiesapi.models.ProductDto
import ru.adavydova.foodiesapi.models.TagDto


interface FoodiesApi {
    @GET("Products.json")
    suspend fun getAllProducts(): Result<List<ProductDto>>
    @GET("Tags.json")
    suspend fun getAllTags(): Result<List<TagDto>>
    @GET("Categories.json")
    suspend fun getAllCategories(): Result<List<CategoryDto>>
}
