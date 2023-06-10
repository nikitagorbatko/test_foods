package com.nikitagorbatko.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.dishes.DishesRepository
import com.nikitagorbatko.network.DishDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DishesViewModel(private val repository: DishesRepository) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _categories = MutableSharedFlow<List<DishDto>>()
    val categories = _categories.asSharedFlow()

    fun getDishes() {
        viewModelScope.launch {
            _state.emit(State.LOADING)
            try {
                val categories = repository.getDishes().dishes
                if (categories.isNotEmpty()) {
                    _categories.emit(categories)
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