package com.nikitagorbatko.database_entities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CartDishDbo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CartDishDatabase : RoomDatabase() {

    abstract fun cartDishDao(): CartDishDao

    companion object {
        private fun getDatabase(context: Context) = Room.databaseBuilder(
            context,
            CartDishDatabase::class.java,
            "cart_dishes_database.db"
        ).build()

        fun getCartDishDao(context: Context) = getDatabase(context).cartDishDao()
    }
}
