package com.nikitagorbatko.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.cart_dishes_use_case.AddCartDishUseCase
import com.nikitagorbatko.database_entities.CartDishDbo
import kotlinx.coroutines.launch

class ProductViewModel(private val addDishesUseCase: AddCartDishUseCase) : ViewModel() {
    fun addDish(dish: CartDishDbo) {
        viewModelScope.launch {
            addDishesUseCase(dish)
        }
    }
}