package com.alexius.shopy.presentation.resetpass

data class ResetPassState(
    val email: String = "",
    val emailIsError: Boolean = false
)
