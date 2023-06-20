package com.nikitagorbatko.cart_dishes_use_case

import com.nikitagorbatko.database_entities.CartDishDbo
import com.nikitagorbatko.repositories.CartDishesRepository

class RemoveCartDishUseCase(private val repository: CartDishesRepository) {
    suspend operator fun invoke(dish: CartDishDbo) = repository.removeDish(dish)
}