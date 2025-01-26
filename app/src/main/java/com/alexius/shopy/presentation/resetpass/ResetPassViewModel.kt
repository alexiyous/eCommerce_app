package com.alexius.shopy.presentation.resetpass

import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecase.ResetPassword
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResetPassViewModel(
    private val resetPassword: ResetPassword,
): ViewModel() {
    private val _state = MutableStateFlow(ResetPassState())
    val state: StateFlow<ResetPassState> = _state

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email)
        checkEmailValid()
    }

    private fun checkEmailValid(){
        _state.value = _state.value.copy(
            emailIsError = _state.value.email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(_state.value.email).matches())
    }

    fun resetPass(callbackOnsuccess: () -> Unit, callbackOnFailed: (String) -> Unit) {
        // Reset password logic here
        viewModelScope.launch {
            resetPassword(_state.value.email).collect{result ->
                when(result){
                    is UiState.Success -> {
                        // Create an intent to open Gmail
                        callbackOnsuccess()
                    }
                    is UiState.Error -> {
                        callbackOnFailed(result.errorMessage ?: "An unexpected error occurred")
                    }
                    is UiState.Loading -> {
                        // Do nothing
                    }
                }
            }
        }
    }
}