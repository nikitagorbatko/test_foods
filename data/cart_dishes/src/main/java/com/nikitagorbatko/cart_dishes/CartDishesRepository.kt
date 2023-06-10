package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.database.DishDbo


interface CartDishesRepository {
    suspend fun getDishes(): List<DishDbo>
}