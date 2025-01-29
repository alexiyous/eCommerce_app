package com.alexius.shopy.presentation.sign_in

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecase.SaveAppEntry
import com.alexius.core.domain.usecase.SignInWithEmail
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInWithEmail: SignInWithEmail,
    private val saveAppEntry: SaveAppEntry
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state

    init {
        checkEmailValid()
        checkPasswordValid()
    }

    fun signIn( onSignInFailed: (String) -> Unit) {
        viewModelScope.launch {
            signInWithEmail(_state.value.email, _state.value.password).collect{result ->
                when(result){
                    is UiState.Success -> {
                        saveAppEntry(true)
                        Log.d("SignInViewModel", "signInWithEmail: ${result.data}")
                        _state.value = _state.value.copy(isLoading = false)
                    }
                    is UiState.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _state.value = _state.value.copy(errorMessage = result.errorMessage ?: "An unexpected error occurred")
                        onSignInFailed(_state.value.errorMessage)
                    }
                    is UiState.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
        checkEmailValid()
    }

    private fun checkEmailValid(){
        _state.value = _state.value.copy(
            emailIsError = _state.value.email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches())
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password)
        checkPasswordValid()
    }

    private fun checkPasswordValid(){
        _state.value = _state.value.copy(passwordIsError = _state.value.password.isEmpty() || _state.value.password.length < 6)
    }
}