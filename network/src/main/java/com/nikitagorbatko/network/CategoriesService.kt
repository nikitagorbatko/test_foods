package com.nikitagorbatko.network

import retrofit2.http.*

interface CategoriesService {
    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryResponseDto
}