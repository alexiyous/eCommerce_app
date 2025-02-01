package com.alexius.shopy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecase.GetProductsUseCase
import com.alexius.core.domain.usecase.GetUserInfo
import com.alexius.core.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getUserInfo: GetUserInfo
): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        getProducts()
        fetchUserInfo()
    }

    fun getProducts() {
        viewModelScope.launch(){
            getProductsUseCase().collect{ result ->
                when (result) {
                    is UiState.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is UiState.Success -> {
                        _state.value = _state.value.copy(products = result.data, isLoading = false)
                    }

                    is UiState.Error -> {
                        _state.value = _state.value.copy(error = result.errorMessage, isLoading = false)
                    }
                }
            }
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch() {
            getUserInfo().collect { result ->
                when (result) {
                    is UiState.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is UiState.Success -> {
                        _state.value = _state.value.copy(userInfo = result.data, isLoading = false)
                    }

                    is UiState.Error -> {
                        _state.value = _state.value.copy(error = result.errorMessage, isLoading = false)
                    }
                }
            }
        }
    }

}