package com.nikitagorbatko.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitagorbatko.entity.Dish

@Entity(tableName = "cart_dishes_table")
data class DishDbo(
    @PrimaryKey
    @ColumnInfo(name = "cart_id") val cartId: Int,
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "name") override val name: String? = null,
    @ColumnInfo(name = "price") override val price: Int? = null,
    @ColumnInfo(name = "weight") override val weight: Int? = null,
    @ColumnInfo(name = "description") override val description: String? = null,
    @ColumnInfo(name = "image_url") override val imageUrl: String? = null,
    @ColumnInfo(name = "tegs") override val tegs: List<String> = listOf(),
    @ColumnInfo(name = "count") var count: Int,
) : Dish