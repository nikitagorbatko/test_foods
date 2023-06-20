package com.nikitagorbatko.repositories

import com.nikitagorbatko.network.CategoryResponseDto

interface CategoriesRepository {
    suspend fun getCategories(): CategoryResponseDto
}