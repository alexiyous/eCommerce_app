package com.alexius.shopy.presentation.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecase.UploadImageProfile
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val uploadImageProfile: UploadImageProfile
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state

    fun uploadProfileImage(bitmap: Bitmap, onSuccessUploading: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            uploadImageProfile(bitmap).collect{ result ->
                when (result) {
                    is UiState.Loading -> {
                        // Handle loading state
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is UiState.Success -> {
                        // Handle success state
                        _state.value = _state.value.copy(isLoading = false)
                        onSuccessUploading(result.data)
                    }

                    is UiState.Error -> {
                        // Handle error state
                        _state.value = _state.value.copy(isLoading = false, error = result.errorMessage ?: "An unexpected error occurred")
                        onError(result.errorMessage ?: "An unexpected error occurred")
                    }
                }

            }
        }
    }
}