package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.database.DishDao
import com.nikitagorbatko.database.DishDatabase
import com.nikitagorbatko.database.DishDbo
import kotlinx.coroutines.flow.Flow

class CartDishesRepositoryImpl(private val database: DishDatabase) : CartDishesRepository {
    override suspend fun getDishes(): List<DishDbo> {
        return database.getDao().getAllDishes()
    }
}