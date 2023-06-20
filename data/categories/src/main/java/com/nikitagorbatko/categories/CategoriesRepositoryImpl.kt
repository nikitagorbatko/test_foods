package com.nikitagorbatko.categories

import com.nikitagorbatko.network.CategoriesService
import com.nikitagorbatko.network.CategoryResponseDto
import com.nikitagorbatko.repositories.CategoriesRepository

class CategoriesRepositoryImpl(private val service: CategoriesService) : CategoriesRepository {
    override suspend fun getCategories(): CategoryResponseDto {
        return service.getCategories()
    }
}