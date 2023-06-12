package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.entity.Dish

class CartDishesRepositoryImpl : CartDishesRepository {
    private val savedDishes = mutableSetOf<CartDish>()

    override fun getDishes() = savedDishes.toList()

    override fun addDish(dish: Dish) {
        var savedIndex = -1
        savedDishes.forEachIndexed { index, cartDish ->
            if (cartDish.id == dish.id) {
                cartDish.amount++
                savedIndex = index
            }
        }
        if (savedIndex == -1) {
            savedDishes.add(
                CartDish(
                    id = dish.id,
                    name = dish.name ?: "",
                    price = dish.price,
                    weight = dish.weight,
                    description = dish.description,
                    imageUrl = dish.imageUrl,
                    tags = dish.tags,
                    amount = 1
                )
            )
        }
    }

    override fun minusDish(dish: Dish): Boolean {
        savedDishes.forEach {
            if (it.id == dish.id) { it.amount-- }
        }
        return savedDishes.removeIf { it.amount < 1 }
    }
}