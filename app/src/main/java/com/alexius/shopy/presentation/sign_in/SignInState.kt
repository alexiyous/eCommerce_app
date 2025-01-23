package com.alexius.shopy.presentation.sign_in

data class SignInState(
    val email: String = "",
    val password: String = "",
    val emailIsError: Boolean = false,
    val passwordIsError: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
