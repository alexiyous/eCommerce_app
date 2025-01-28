package com.alexius.shopy.presentation.sign_up

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpViewModel: ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    private val regexPassword =
        "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{6,})"

    init {
        checkPasswordValid()
        checkNameValid()
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
        checkPasswordValid()
    }

    private fun checkPasswordValid() {
        _state.value = _state.value.copy(
            passwordIsError = _state.value.password.isEmpty() || !regexPassword.toRegex().matches(_state.value.password),
            containOneLowerCase = _state.value.password.contains(Regex("[a-z]")),
            containOneUpperCase = _state.value.password.contains(Regex("[A-Z]")),
            containOneDigit = _state.value.password.contains(Regex("[0-9]")),
            containOneSpecialChar = _state.value.password.contains(Regex("[^A-Za-z0-9]")),
            containSixChars = _state.value.password.length >= 6
        )
    }

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
        checkNameValid()
    }

    private fun checkNameValid() {
        _state.value = _state.value.copy(
            nameIsError = _state.value.name.isEmpty()
        )
    }
}