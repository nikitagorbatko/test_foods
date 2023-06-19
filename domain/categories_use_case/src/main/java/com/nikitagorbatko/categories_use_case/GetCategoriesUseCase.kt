package com.nikitagorbatko.categories_use_case

import com.nikitagorbatko.categories.CategoriesRepository

class GetCategoriesUseCase(private val repository: CategoriesRepository) {
    suspend fun execute() = repository.getCategories().categories
}