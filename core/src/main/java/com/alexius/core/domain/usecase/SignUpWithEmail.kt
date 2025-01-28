package com.alexius.core.domain.usecase

import com.alexius.core.domain.repository.ProductRepository

class SignUpWithEmail(
    private val repository: ProductRepository
) {
    operator fun invoke(email: String, password: String) = repository.createAccountWithEmailPassword(email, password)
}