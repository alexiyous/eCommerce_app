package com.alexius.core.domain.usecase

import com.alexius.core.domain.repository.ProductRepository

class GetUserInfo(private val repository: ProductRepository) {

    operator fun invoke() = repository.getUserInfoFromFirestore()
}