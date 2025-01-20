package com.alexius.core.domain.model

import com.alexius.core.data.model.remote.Rating

data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
){
    val priceString: String
        get() = "$$price"
}
