package com.nikitagorbatko.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.cart_dishes.CartDish
import com.nikitagorbatko.cart_dishes.CartDishesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartDishesRepository) : ViewModel() {
    private val _totalPrice = Channel<Int>()
    val totalPrice = _totalPrice.receiveAsFlow()

    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _dishes = MutableStateFlow<List<CartDish>>(listOf())
    val dishes = _dishes.asStateFlow()

    fun getDishes() {
        viewModelScope.launch {
            _state.tryEmit(State.LOADING)
            val dishes = repository.getDishes()
            if (dishes.isNotEmpty()) {
                _dishes.emit(dishes)
                _totalPrice.send(dishes.sumOf { (it.price ?: 0) * it.amount })
                _state.emit(State.PRESENT)
            } else {
                _state.emit(State.NO_DISHES)
            }
        }
    }

    fun addDish(cartDish: CartDish) {
        repository.addDish(cartDish)
        viewModelScope.launch {
            val dishes = repository.getDishes()
            _totalPrice.send(dishes.sumOf { (it.price ?: 0) * it.amount })
            _dishes.emit(dishes)
        }
    }

    fun minusDish(cartDish: CartDish) {
        repository.minusDish(cartDish)
        viewModelScope.launch {
            val dishes = repository.getDishes()
            _totalPrice.send(dishes.sumOf { (it.price ?: 0) * it.amount })
            if (dishes.isEmpty()) {
                _state.emit(State.NO_DISHES)
            } else {
                _dishes.emit(dishes)
            }
        }
    }

    enum class State {
        LOADING, PRESENT, NO_DISHES
    }
}