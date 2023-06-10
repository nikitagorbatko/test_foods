package com.nikitagorbatko.dishes

import com.nikitagorbatko.network.DishesResponseDto
import com.nikitagorbatko.network.DishesService

class DishesRepositoryImpl(private val service: DishesService): DishesRepository {
    override suspend fun getDishes(): DishesResponseDto {
        return service.getDishes()
    }
}