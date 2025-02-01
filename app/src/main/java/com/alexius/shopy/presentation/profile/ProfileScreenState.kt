package com.alexius.shopy.presentation.profile

import com.alexius.core.domain.model.UserInfoDomain

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val userInfo: UserInfoDomain? = null
)
