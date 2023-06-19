package com.nikitagorbatko.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.cart_dishes.CartDishesRepository
import com.nikitagorbatko.database_entities.CartDishDbo
import com.nikitagorbatko.entity.Dish
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: CartDishesRepository): ViewModel() {
    fun addDish(dish: CartDishDbo) {
        viewModelScope.launch {
            repository.addDish(dish)
        }
    }
}