package com.alexius.core.domain.repository

import android.graphics.Bitmap
import com.alexius.core.domain.model.Product
import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<UiState<List<Product>>>

    fun signInWithEmailPassword(email: String, password: String): Flow<UiState<Boolean>>

    fun createAccountWithEmailPassword(email: String, password: String): Flow<UiState<Boolean>>

    fun resetPassword(email: String): Flow<UiState<Boolean>>

    fun initUserInfoInFirestore(userInfo: UserInfoDomain): Flow<UiState<Unit>>

    fun getUserInfoFromFirestore(): Flow<UiState<UserInfoDomain>>

    fun uploadImageAndStoreReference(bitmap: Bitmap): Flow<UiState<String>>

    fun updateUserInfoName(name: String): Flow<UiState<Unit>>
}