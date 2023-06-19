package com.nikitagorbatko.database_entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nikitagorbatko.entity.Dish

@Entity(tableName = "cart_dishes")
data class CartDishDbo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") override val id: Int,
    @ColumnInfo(name = "description") override val description: String?,
    @ColumnInfo(name = "name") override val name: String?,
    @ColumnInfo(name = "price") override val price: Int?,
    @ColumnInfo(name = "weight") override val weight: Int?,
    @ColumnInfo(name = "image_url") override val imageUrl: String?,
    @ColumnInfo(name = "tags") override val tags: List<String>,
    @ColumnInfo(name = "amount") var amount: Int
) : Dish
