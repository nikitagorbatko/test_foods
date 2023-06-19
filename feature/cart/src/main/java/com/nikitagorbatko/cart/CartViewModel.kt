package com.nikitagorbatko.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.cart_dishes.CartDish
import com.nikitagorbatko.cart_dishes.CartDishesRepository
import com.nikitagorbatko.cart_dishes_use_case.AddCartDishUseCase
import com.nikitagorbatko.cart_dishes_use_case.GetCartDishesUseCase
import com.nikitagorbatko.cart_dishes_use_case.RemoveCartDishUseCase
import com.nikitagorbatko.database_entities.CartDishDbo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartDishesUseCase: GetCartDishesUseCase,
    private val addCartDishesUseCase: AddCartDishUseCase,
    private val removeCartDishUseCase: RemoveCartDishUseCase
) : ViewModel() {
    private val _totalPrice = Channel<Int>()
    val totalPrice = _totalPrice.receiveAsFlow()

    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _dishes = MutableStateFlow<List<CartDishDbo>>(listOf())
    val dishes = _dishes.asStateFlow()

    fun getDishes() {
        viewModelScope.launch {
            _state.tryEmit(State.LOADING)
            getCartDishesUseCase.execute().collect { dishes ->
                if (dishes.isNotEmpty()) {
                    _dishes.emit(dishes)
                    _totalPrice.send(dishes.sumOf { (it.price ?: 0) * it.amount })
                    _state.emit(State.PRESENT)
                } else {
                    _state.emit(State.NO_DISHES)
                }
            }
        }
    }

    fun addDish(cartDish: CartDishDbo) {
        viewModelScope.launch {
            addCartDishesUseCase.execute(cartDish)
//            val dishes = getCartDishesUseCase.execute()
//            dishes.singleOrNull()?.sumOf { (it.price ?: 0) * it.amount }?.let { _totalPrice.send(it) }
            //_dishes.emit(dishes)
        }
    }

    fun minusDish(cartDish: CartDishDbo) {
        viewModelScope.launch {
            removeCartDishUseCase.execute(cartDish)
        }
//        viewModelScope.launch {
//            val dishes = getCartDishesUseCase.execute()
//            _totalPrice.send(dishes.sumOf { (it.price ?: 0) * it.amount })
//            if (dishes.isEmpty()) {
//                _state.emit(State.NO_DISHES)
//            } else {
//                _dishes.emit(dishes)
//            }
//        }
    }

    enum class State {
        LOADING, PRESENT, NO_DISHES
    }
}