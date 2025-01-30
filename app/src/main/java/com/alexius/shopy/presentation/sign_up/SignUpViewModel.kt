package com.alexius.shopy.presentation.sign_up

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.core.domain.usecase.CreateInitUserFirestore
import com.alexius.core.domain.usecase.SignUpWithEmail
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpWithEmail: SignUpWithEmail,
    private val createInitUserFirestore: CreateInitUserFirestore,
): ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    private val regexPassword =
        "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{6,})"

    init {
        checkPasswordValid()
        checkNameValid()
        checkEmailValid()
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
        checkPasswordValid()
    }

    private fun checkPasswordValid() {
        _state.value = _state.value.copy(
            passwordIsError = !regexPassword.toRegex().containsMatchIn(_state.value.password),
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
            nameIsError = _state.value.name.isEmpty() || _state.value.name.length < 3
        )
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
        checkEmailValid()
    }

    private fun checkEmailValid() {
        _state.value = _state.value.copy(
            emailIsError = _state.value.email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches()
        )
    }

    fun signUp(onSignUpSuccess: () -> Unit, onSignUpFailed: (String) -> Unit) {
        viewModelScope.launch {
            val email = _state.value.email
            val password = _state.value.password

            signUpWithEmail(email, password).collect{ result ->
                when(result) {
                    is UiState.Success -> {
                        //Create user profile in firestore
                        createUserInfo(onSignUpSuccess, onSignUpFailed)
                    }
                    is UiState.Error -> {
                        // Handle error
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(errorMessage = result.errorMessage ?: "An unexpected error occurred")
                        onSignUpFailed(_state.value.errorMessage)
                    }
                    is UiState.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun createUserInfo(onSignUpSuccess: () -> Unit, onSignUpFailed: (String) -> Unit) {
        viewModelScope.launch {
            val userInfo = UserInfoDomain(
                email = _state.value.email,
                name = _state.value.name,
                profileImage = "",
            )

            createInitUserFirestore(userInfo).collect { result ->
                when(result) {
                    is UiState.Success -> {
                        // Handle success
                        onSignUpSuccess()
                        _state.value = _state.value.copy(isLoading = false)
                    }
                    is UiState.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(errorMessage = result.errorMessage ?: "An unexpected error occurred")
                        onSignUpFailed(_state.value.errorMessage)
                    }
                    is UiState.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}