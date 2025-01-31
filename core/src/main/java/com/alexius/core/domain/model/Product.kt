package com.alexius.core.domain.model

import android.os.Parcelable
import com.alexius.core.data.model.remote.Rating
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
) : Parcelable

{
    val priceString: String
        get() = "$$price"
}
