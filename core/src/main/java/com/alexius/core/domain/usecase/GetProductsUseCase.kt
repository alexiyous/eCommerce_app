package com.alexius.core.domain.usecase

import com.alexius.core.domain.repository.ProductRepository

class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke() = productRepository.getProducts()
}