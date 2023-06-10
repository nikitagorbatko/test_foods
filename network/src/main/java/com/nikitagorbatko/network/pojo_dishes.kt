package com.nikitagorbatko.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DishDto(
    @Json(name = "id") var id: Int,
    @Json(name = "name") var name: String? = null,
    @Json(name = "price") var price: Int? = null,
    @Json(name = "weight") var weight: Int? = null,
    @Json(name = "description") var description: String? = null,
    @Json(name = "image_url") var imageUrl: String? = null,
    @Json(name = "tegs") var tegs: List<String> = listOf()
) : Dish

interface Dish {}

@JsonClass(generateAdapter = true)
data class DishesResponseDto(
    @Json(name = "dishes") var dishes: List<DishDto> = listOf()
)