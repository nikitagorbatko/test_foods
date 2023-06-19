package com.nikitagorbatko.network

import android.os.Parcelable
import com.nikitagorbatko.entity.Dish
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DishDto(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String? = null,
    @Json(name = "price") override val price: Int? = null,
    @Json(name = "weight") override val weight: Int? = null,
    @Json(name = "description") override val description: String? = null,
    @Json(name = "image_url") override val imageUrl: String? = null,
    @Json(name = "tegs") override val tags: List<String> = listOf()
) : Dish, Parcelable




@JsonClass(generateAdapter = true)
data class DishesResponseDto(
    @Json(name = "dishes") val dishes: List<DishDto> = listOf()
)