package com.nikitagorbatko.repositories

import com.nikitagorbatko.network.DishesResponseDto

interface DishesRepository {
    suspend fun getDishes(): DishesResponseDto
}