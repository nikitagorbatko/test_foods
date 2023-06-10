package com.nikitagorbatko.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [DishDbo::class])
abstract class DishDatabase : RoomDatabase() {
    abstract fun getDao(): DishDao

    companion object {

        @Volatile
        private var INSTANCE: DishDatabase? = null

        fun getInstance(context: Context): DishDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DishDatabase::class.java, "cart_dishes.db"
            )
                .build()
    }
}

