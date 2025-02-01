package com.alexius.core.domain.usecase

import com.alexius.core.domain.repository.ProductRepository

class UpdateUserNameOnline(private val productRepository: ProductRepository) {
    operator fun invoke(name: String) = productRepository.updateUserInfoName(name)
}