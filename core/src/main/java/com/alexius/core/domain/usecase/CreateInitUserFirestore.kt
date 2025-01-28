package com.alexius.core.domain.usecase

import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.core.domain.repository.ProductRepository
import com.alexius.core.util.UiState
import kotlinx.coroutines.flow.Flow

class CreateInitUserFirestore(
    private val productRepository: ProductRepository
) {
    operator fun invoke(userInfo: UserInfoDomain): Flow<UiState<Unit>> {
        return productRepository.initUserInfoInFirestore(userInfo)
    }
}