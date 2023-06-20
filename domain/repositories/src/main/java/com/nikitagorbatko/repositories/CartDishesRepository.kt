package com.nikitagorbatko.repositories

import com.nikitagorbatko.database_entities.CartDishDbo
import kotlinx.coroutines.flow.Flow


interface CartDishesRepository {
    fun getDishes(): Flow<List<CartDishDbo>>

    suspend fun addDish(dish: CartDishDbo)

    suspend fun removeDish(dish: CartDishDbo): Boolean // returns true if removes item
}