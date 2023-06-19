package com.nikitagorbatko.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.dishes.DishesRepository
import com.nikitagorbatko.dishes_use_case.GetDishesUseCase
import com.nikitagorbatko.network.DishDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DishesViewModel(private val getDishesUseCase: GetDishesUseCase) : ViewModel() {
    val dishTags = mutableListOf<String>()

    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _categories = MutableSharedFlow<List<DishDto>>()
    val categories = _categories.asSharedFlow()

    fun getDishes(checkedChips: List<String>? = null) {
        viewModelScope.launch {
            _state.emit(State.LOADING)
            try {
                val categories = getDishesUseCase.execute()
                if (categories.isNotEmpty()) {
                    if (checkedChips != null && checkedChips.isNotEmpty()) {
                        _categories.emit(
                            categories.filter {
                                checkedChips.intersect(it.tags.toSet()).isNotEmpty()
                            }
                        )
                    } else {
                        _categories.emit(categories)
                    }
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