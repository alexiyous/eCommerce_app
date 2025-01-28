package com.alexius.shopy.presentation.sign_up

data class SignUpState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val name: String = "",
    val nameIsError: Boolean = false,
    val email: String = "",
    val emailIsError: Boolean = false,
    val password: String = "",
    val passwordIsError: Boolean = false,
    val containOneLowerCase: Boolean = false,
    val containOneUpperCase: Boolean = false,
    val containOneDigit: Boolean = false,
    val containOneSpecialChar: Boolean = false,
    val containSixChars: Boolean = false
)
