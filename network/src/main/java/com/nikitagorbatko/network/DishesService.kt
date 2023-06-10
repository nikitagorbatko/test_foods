package com.nikitagorbatko.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface DishesService {
    @GET("c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishes(): DishesResponseDto
}