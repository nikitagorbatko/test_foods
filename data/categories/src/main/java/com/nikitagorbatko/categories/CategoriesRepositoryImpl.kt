package com.nikitagorbatko.categories

import android.util.Log
import com.nikitagorbatko.network.CategoriesService
import com.nikitagorbatko.network.CategoryResponseDto
import com.nikitagorbatko.network.DishesResponseDto
import com.nikitagorbatko.network.DishesService

class CategoriesRepositoryImpl(private val service: CategoriesService): CategoriesRepository {
    override suspend fun getCategories(): CategoryResponseDto {
        return service.getCategories()
    }
}