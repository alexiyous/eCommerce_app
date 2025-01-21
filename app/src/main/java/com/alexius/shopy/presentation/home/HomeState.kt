package com.alexius.shopy.presentation.home

import com.alexius.core.domain.model.Product

data class HomeState(
    val isLoading: Boolean = false,
    val data: List<Product> = emptyList(),
    val error: String = ""
)
