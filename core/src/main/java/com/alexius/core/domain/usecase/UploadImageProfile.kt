package com.alexius.core.domain.usecase

import android.graphics.Bitmap
import com.alexius.core.domain.repository.ProductRepository
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.Flow

class UploadImageProfile(
    private val repository: ProductRepository
) {
    operator fun invoke(bitmap: Bitmap): Flow<UiState<String>> {
        return repository.uploadImageAndStoreReference(bitmap)
    }
}