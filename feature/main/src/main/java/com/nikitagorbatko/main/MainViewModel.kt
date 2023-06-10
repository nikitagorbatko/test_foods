package com.nikitagorbatko.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikitagorbatko.categories.CategoriesRepository
import com.nikitagorbatko.network.CategoryDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CategoriesRepository) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state = _state.asStateFlow()

    private val _categories = MutableSharedFlow<List<CategoryDto>>()
    val categories = _categories.asSharedFlow()

    fun getCategories() {
        viewModelScope.launch {
            _state.emit(State.LOADING)
            try {
                val categories = repository.getCategories().categories
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