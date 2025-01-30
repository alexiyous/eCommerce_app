package com.alexius.shopy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecase.GetProductsUseCase
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch{
            getProductsUseCase().collect{ result ->
                when (result) {
                    is UiState.Loading -> {
                        _state.value = HomeState(isLoading = true)
                    }

                    is UiState.Success -> {
                        _state.value = HomeState(products = result.data)
                    }

                    is UiState.Error -> {
                        _state.value = HomeState(error = result.errorMessage)
                    }
                }
            }
        }
    }
}