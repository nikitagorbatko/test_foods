package com.nikitagorbatko.category

import androidx.lifecycle.ViewModel
import com.nikitagorbatko.cart_dishes.CartDishesRepository
import com.nikitagorbatko.entity.Dish

class ProductViewModel(private val repository: CartDishesRepository): ViewModel() {
    fun addDish(dish: Dish) {
        repository.addDish(dish)
    }
}