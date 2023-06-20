package com.nikitagorbatko.cart_dishes_use_case

import com.nikitagorbatko.repositories.CartDishesRepository

class GetCartDishesUseCase(private val repository: CartDishesRepository) {
    operator fun invoke() = repository.getDishes()
}