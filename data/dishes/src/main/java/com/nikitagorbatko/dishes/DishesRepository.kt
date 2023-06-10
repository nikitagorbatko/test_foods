package com.nikitagorbatko.dishes

import com.nikitagorbatko.network.DishesResponseDto

interface DishesRepository {
    suspend fun getDishes(): DishesResponseDto
}