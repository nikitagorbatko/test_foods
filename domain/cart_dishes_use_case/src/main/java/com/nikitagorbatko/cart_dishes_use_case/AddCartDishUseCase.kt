package com.nikitagorbatko.cart_dishes_use_case

import com.nikitagorbatko.database_entities.CartDishDbo
import com.nikitagorbatko.repositories.CartDishesRepository

class AddCartDishUseCase(private val repository: CartDishesRepository) {
    suspend operator fun invoke(dish: CartDishDbo) = repository.addDish(dish)
}