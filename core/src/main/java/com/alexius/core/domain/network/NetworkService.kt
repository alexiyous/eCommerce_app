package com.alexius.core.domain.network

import com.alexius.core.data.model.remote.ProductsDtoItem
import com.alexius.core.util.UiState

interface NetworkService {

    suspend fun getProducts(): List<ProductsDtoItem>

}