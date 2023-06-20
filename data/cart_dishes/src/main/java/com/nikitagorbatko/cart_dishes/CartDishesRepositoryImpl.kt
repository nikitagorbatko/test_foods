package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.database_entities.CartDishDao
import com.nikitagorbatko.database_entities.CartDishDbo
import com.nikitagorbatko.repositories.CartDishesRepository

class CartDishesRepositoryImpl(private val cartDishDao: CartDishDao) : CartDishesRepository {
    override fun getDishes() = cartDishDao.getCartDishes()

    override suspend fun addDish(dish: CartDishDbo) {
        val savedDish = cartDishDao.getDish(dish.id)
        if (savedDish != null) {
            cartDishDao.updateDish(savedDish.also { it.amount++ })
        } else {
            cartDishDao.insertDish(
                CartDishDbo(
                    id = dish.id,
                    description = dish.description,
                    name = dish.name,
                    price = dish.price,
                    weight = dish.weight,
                    imageUrl = dish.imageUrl,
                    tags = dish.tags,
                    amount = 1
                )
            )
        }
    }

    override suspend fun removeDish(dish: CartDishDbo): Boolean {
        val savedDish = cartDishDao.getDish(dish.id)
        return if (savedDish != null && savedDish.amount > 1) {
            cartDishDao.updateDish(savedDish.also { it.amount-- })
            false
        } else {
            cartDishDao.deleteCartDish(dish.id) > 0
        }
    }
}