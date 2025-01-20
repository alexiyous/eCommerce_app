package com.alexius.core.data.model.remote

import com.alexius.core.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductsDtoItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)

fun ProductsDtoItem.toProduct() = Product(
    category = category,
    description = description,
    id = id,
    image = image,
    price = price,
    title = title
)

