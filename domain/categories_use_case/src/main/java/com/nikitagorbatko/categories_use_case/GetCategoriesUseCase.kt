package com.nikitagorbatko.categories_use_case

import com.nikitagorbatko.repositories.CategoriesRepository

class GetCategoriesUseCase(private val repository: CategoriesRepository) {
    suspend operator fun invoke() = repository.getCategories().categories
}