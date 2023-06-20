package com.nikitagorbatko.dishes_use_case

import com.nikitagorbatko.repositories.DishesRepository

class GetDishesUseCase(private val repository: DishesRepository) {
    suspend operator fun invoke() = repository.getDishes().dishes
}