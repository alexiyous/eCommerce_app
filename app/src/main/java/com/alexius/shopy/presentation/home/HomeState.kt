package com.alexius.shopy.presentation.home

import com.alexius.core.domain.model.Product
import com.alexius.core.domain.model.UserInfoDomain

data class HomeState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = "",
    val userInfo: UserInfoDomain = UserInfoDomain("", "", "")
)
