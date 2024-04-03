package ru.adavydova.foodies_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.adavydova.foodies_database.models.BasketDBO

@Dao
interface BasketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(basketDBO: BasketDBO)

    @Query("DELETE  FROM basket WHERE ID =:id")
    suspend fun deleteProduct(id: Long)

    @Query("SELECT * FROM basket")
    fun getAllProducts(): Flow<List<BasketDBO>>

    @Query("UPDATE basket SET NUM_OF_PRICES=:newNum WHERE ID =:id")
    suspend fun changeNumOfProductFromCart(newNum: Int, id: Long)

    @Query("SELECT * FROM basket WHERE ID=:id")
    suspend fun getBasketById(id:Long): BasketDBO
}