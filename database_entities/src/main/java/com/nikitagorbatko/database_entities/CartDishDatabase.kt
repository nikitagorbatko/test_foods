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

        @Volatile
        private var INSTANCE: CartDishDatabase? = null

        fun getCartDishDao(context: Context): CartDishDao {
            return if (INSTANCE != null) {
                INSTANCE!!.cartDishDao()
            } else {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = buildDatabase(context)
                    }
                    INSTANCE!!.cartDishDao()
                }
            }
        }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CartDishDatabase::class.java, "cart_dishes_database.db"
            )
                .build()
    }
}
