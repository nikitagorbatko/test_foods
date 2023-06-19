package com.nikitagorbatko.cart_dishes_use_case

import com.nikitagorbatko.cart_dishes.CartDishesRepository
import com.nikitagorbatko.database_entities.CartDishDbo
import com.nikitagorbatko.entity.Dish

class RemoveCartDishUseCase(private val repository: CartDishesRepository) {
    suspend fun execute(dish: CartDishDbo) = repository.removeDish(dish)
}