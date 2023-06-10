package com.nikitagorbatko.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDto(
    @Json(name = "id") var id: Int,
    @Json(name = "name") var name: String? = null,
    @Json(name = "image_url") var imageUrl: String? = null
): Category

interface Category {}

@JsonClass(generateAdapter = true)
data class CategoryResponseDto(
    @Json(name = "сategories") var categories: List<CategoryDto> = listOf()
)