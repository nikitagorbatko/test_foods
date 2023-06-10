package com.nikitagorbatko.network

import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://run.mocky.io/v3/"

class Retrofit {
    private val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun getCategoriesService(): CategoriesService = retrofit.create(CategoriesService::class.java)

    fun getDishesService(): DishesService = retrofit.create(DishesService::class.java)
}