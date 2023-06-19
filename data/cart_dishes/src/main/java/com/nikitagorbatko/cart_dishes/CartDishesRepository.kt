package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.database_entities.CartDishDbo
import com.nikitagorbatko.entity.Dish
import kotlinx.coroutines.flow.Flow


interface CartDishesRepository {
    fun getDishes(): Flow<List<CartDishDbo>>

    suspend fun addDish(dish: CartDishDbo)

    suspend fun removeDish(dish: CartDishDbo): Boolean // returns true if removes item
}