package com.nikitagorbatko.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.cart_dishes.CartDishesRepository
import com.nikitagorbatko.database.DishDbo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartDishesRepository) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _dishes = MutableSharedFlow<List<DishDbo>>()
    val dishes = _dishes.asSharedFlow()

    fun getDishes() {
        viewModelScope.launch {
            _state.emit(State.LOADING)
            try {
                val dishes = repository.getDishes()
                if (dishes.isNotEmpty()) {
                    _dishes.emit(dishes)
                    _state.emit(State.PRESENT)
                } else {
                    _state.emit(State.ERROR)
                }
            } catch (_: Exception) {
                _state.emit(State.ERROR)
            }
        }
    }

    enum class State {
        LOADING, PRESENT, ERROR
    }
}