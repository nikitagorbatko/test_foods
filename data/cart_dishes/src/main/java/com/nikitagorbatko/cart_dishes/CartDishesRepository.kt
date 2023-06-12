package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.entity.Dish


interface CartDishesRepository {
    fun getDishes(): List<CartDish>

    fun addDish(dish: Dish)

    fun minusDish(dish: Dish): Boolean // returns true if removes item
}