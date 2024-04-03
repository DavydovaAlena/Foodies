package ru.adavydova.foodies_data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import ru.adavydova.foodies_data.mapper.toBasket
import ru.adavydova.foodies_data.mapper.toBasketDBO
import ru.adavydova.foodies_data.mapper.toCategory
import ru.adavydova.foodies_data.mapper.toProduct
import ru.adavydova.foodies_data.mapper.toTag
import ru.adavydova.foodies_data.models.Basket
import ru.adavydova.foodies_data.models.Category
import ru.adavydova.foodies_data.models.Product
import ru.adavydova.foodies_data.models.Tag
import ru.adavydova.foodies_data.utils.Result
import ru.adavydova.foodies_data.utils.handleThrowable
import ru.adavydova.foodies_database.dao.BasketDao
import ru.adavydova.foodiesapi.FoodiesApi


interface ProductRepository {
    suspend fun getAllProducts(): Result<Flow<List<Product>>>
    suspend fun getAllTags(): Result<Flow<HashMap<Tag, Boolean>>>
    suspend fun getAllCategories(): Result<Flow<List<Category>>>
    suspend fun getProductsByCategory(category: Category): Result<Flow<List<Product>>>
    suspend fun getProductsByTags(tags: List<Tag>, category: Category): Result<Flow<List<Product>>>
    suspend fun searchProducts(query: String): Result<Flow<List<Product>>>
    suspend fun addProductToCart(product: Product, numOfPrices: Int): Result<Unit>
    suspend fun removeProductFromCart(id: Long): Result<Unit>
    suspend fun getAllProductsFromCart(): Result<Flow<List<Basket>>>
    suspend fun changeNumOfProductFromCart(newNum: Int, id: Long): Result<Unit>
    suspend fun getBasketById(id: Long): Result<Basket>
    suspend fun addBasketToCart(basket: Basket): Result<Unit>

}

class ProductRepositoryImpl(
    private val api: FoodiesApi,
    private val dao: BasketDao
) : ProductRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getBasketById(id: Long): Result<Basket> {
        return try {
            val basket = dao.getBasketById(id).toBasket()
            Result.Success(
                data = basket
            )
        } catch (e:Exception){
            Result.Error(
                error = e.handleThrowable()
            )
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun addBasketToCart(basket: Basket): Result<Unit> {
        return try {
            dao.insertProduct(basket.toBasketDBO())
            Result.Success(
                data = Unit
            )
        } catch (e: Throwable) {
            Result.Error(e.handleThrowable())
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun changeNumOfProductFromCart(newNum: Int, id: Long): Result<Unit> {
        return try {
            dao.changeNumOfProductFromCart(newNum, id)
            Result.Success(
                data = Unit
            )
        } catch (e: Throwable) {
            Result.Error(
                error = e.handleThrowable()
            )
        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun searchProducts(query: String): Result<Flow<List<Product>>> {
        val result = api.getAllProducts().fold(
            onSuccess = { productDto ->
                val searchValues = productDto.filter {
                    it.name.contains(query, ignoreCase = true)
                }.map { it.toProduct() }
                Result.Success(
                    data = flowOf(searchValues)
                )
            },
            onFailure = {
                Result.Error(
                    error = it.handleThrowable()
                )
            }
        )
        return result
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun addProductToCart(product: Product, numOfPrices: Int): Result<Unit> {
        return try {
            dao.insertProduct(product.toBasketDBO(numOfPrices = numOfPrices))
            Result.Success(
                data = Unit
            )
        } catch (e: Throwable) {
            Result.Error(e.handleThrowable())
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun removeProductFromCart(id: Long): Result<Unit> {
        return try {
            dao.deleteProduct(id)
            Result.Success(
                data = Unit
            )
        } catch (e: Throwable) {
            Result.Error(e.handleThrowable())
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getAllProductsFromCart(): Result<Flow<List<Basket>>> {
        return try {
            val result = dao.getAllProducts()
            Result.Success(
                data = result.map { listBasketDBO->
                    listBasketDBO.map { it.toBasket() }}
            )
        } catch (e:Throwable){
            Result.Error(e.handleThrowable())
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getAllProducts(): Result<Flow<List<Product>>> {
        val result = api.getAllProducts().fold(
            onSuccess = { productsDto ->
                val product = productsDto.map { it.toProduct() }
                Result.Success(
                    data = flowOf(product)
                )
            },
            onFailure = {
                Result.Error(
                    error = it.handleThrowable()
                )
            }
        )
        return result
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getProductsByTags(
        tags: List<Tag>,
        category: Category
    ): Result<Flow<List<Product>>> {
        val selectedTagsId = tags.map { it.id.toInt() }
        val result = api.getAllProducts().fold(
            onSuccess = { productsDto ->
                val productsDtoByCategory = productsDto.filter { it.categoryId == category.id }
                val productsWithTag = productsDtoByCategory.filter { productDto ->
                    var selectState = false
                    for (i in tags.indices) {
                        if (productDto.tagIds.contains(selectedTagsId[i])) {
                            selectState = true
                            break
                        }
                    }
                    selectState
                }

                Result.Success(
                    data = flowOf(productsWithTag.map { productDto -> productDto.toProduct() })
                )
            },
            onFailure = {
                Result.Error(
                    error = it.handleThrowable()
                )
            }
        )
        return result
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getProductsByCategory(category: Category): Result<Flow<List<Product>>> {
        val result = api.getAllProducts().fold(
            onSuccess = { productsDto ->
                Result.Success(
                    data = flowOf(
                        productsDto.filter { it.categoryId == category.id }.map { it.toProduct() }
                    )
                )
            },
            onFailure = {
                Result.Error(
                    error = it.handleThrowable()
                )
            }
        )
        return result
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getAllTags(): Result<Flow<HashMap<Tag, Boolean>>> {
        val result = api.getAllTags().fold(
            onSuccess = { tagsDto ->
                val mapWithTags = hashMapOf<Tag, Boolean>()
                tagsDto.forEach { mapWithTags[it.toTag()] = false }
                Result.Success(
                    data = flowOf(mapWithTags)
                )
            },
            onFailure = {
                Result.Error(
                    error = it.handleThrowable()
                )
            }
        )

        return result
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getAllCategories(): Result<Flow<List<Category>>> {
        val result = api.getAllCategories().fold(
            onSuccess = { categoriesDto ->
                val categories = categoriesDto.map { it.toCategory() }
                Result.Success(
                    data = flowOf(categories)
                )
            },
            onFailure = {
                Result.Error(
                    error = it.handleThrowable()
                )
            }
        )
        return result
    }
}
