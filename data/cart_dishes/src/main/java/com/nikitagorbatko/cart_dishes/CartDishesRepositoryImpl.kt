package com.nikitagorbatko.cart_dishes

import com.nikitagorbatko.database_entities.CartDishDao
import com.nikitagorbatko.database_entities.CartDishDbo

class CartDishesRepositoryImpl(private val cartDishDao: CartDishDao) : CartDishesRepository {
    //private val savedDishes = mutableSetOf<CartDish>()

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
//        var savedIndex = -1
//        savedDishes.forEachIndexed { index, cartDish ->
//            if (cartDish.id == dish.id) {
//                cartDish.amount++
//                savedIndex = index
//            }
//        }
//        if (savedIndex == -1) {
//            savedDishes.add(
//                CartDish(
//                    id = dish.id,
//                    name = dish.name ?: "",
//                    price = dish.price,
//                    weight = dish.weight,
//                    description = dish.description,
//                    imageUrl = dish.imageUrl,
//                    tags = dish.tags,
//                    amount = 1
//                )
//            )
//        }
    }

    override suspend fun removeDish(dish: CartDishDbo): Boolean {

//        savedDishes.forEach {
//            if (it.id == dish.id) {
//                it.amount--
//            }
//        }
//        return savedDishes.removeIf { it.amount < 1 }
        return cartDishDao.deleteCartDish(dish.id) > 0
    }
}