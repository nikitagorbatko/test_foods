package com.nikitagorbatko.dishes_use_case

import com.nikitagorbatko.dishes.DishesRepository

class GetDishesUseCase(private val repository: DishesRepository) {
    suspend fun execute() = repository.getDishes().dishes
}