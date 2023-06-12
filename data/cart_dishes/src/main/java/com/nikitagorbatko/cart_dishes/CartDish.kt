package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.entity.Dish

data class CartDish(
    override val id: Int,
    override val name: String,
    override val price: Int?,
    override val weight: Int?,
    override val description: String?,
    override val imageUrl: String?,
    override val tags: List<String>,
    var amount: Int
): Dish
