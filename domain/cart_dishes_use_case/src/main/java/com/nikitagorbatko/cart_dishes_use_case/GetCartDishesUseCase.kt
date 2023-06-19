package com.nikitagorbatko.cart_dishes_use_case

import com.nikitagorbatko.cart_dishes.CartDishesRepository

class GetCartDishesUseCase(private val repository: CartDishesRepository) {
    fun execute() = repository.getDishes()
}