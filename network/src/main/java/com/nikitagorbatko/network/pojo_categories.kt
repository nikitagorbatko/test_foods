package com.nikitagorbatko.network

import com.nikitagorbatko.entity.Category
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String? = null,
    @Json(name = "image_url") override val imageUrl: String? = null
): Category


@JsonClass(generateAdapter = true)
data class CategoryResponseDto(
    @Json(name = "сategories") val categories: List<CategoryDto> = listOf()
)