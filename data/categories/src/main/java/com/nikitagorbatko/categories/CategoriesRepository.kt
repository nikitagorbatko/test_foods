package com.nikitagorbatko.categories

import com.nikitagorbatko.network.CategoryResponseDto

interface CategoriesRepository {
    suspend fun getCategories(): CategoryResponseDto
}