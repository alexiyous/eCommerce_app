package com.alexius.core.domain.repository

import com.alexius.core.domain.model.Product
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<UiState<List<Product>>>

    fun signInWithEmailPassword(email: String, password: String): Flow<UiState<Boolean>>

    fun resetPassword(email: String): Flow<UiState<Boolean>>
}