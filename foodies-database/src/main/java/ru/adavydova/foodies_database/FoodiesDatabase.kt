package ru.adavydova.foodies_database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.adavydova.foodies_database.dao.BasketDao
import ru.adavydova.foodies_database.models.BasketDBO

@Database(entities = [BasketDBO::class], version = 1)
abstract class FoodiesDatabase : RoomDatabase() {
    abstract val basketDao: BasketDao
}