package com.alexius.core.domain.usecase

import com.alexius.core.domain.repository.ProductRepository

class ResetPassword(
    private val repository: ProductRepository
) {
    operator fun invoke(email: String) = repository.resetPassword(email)
}