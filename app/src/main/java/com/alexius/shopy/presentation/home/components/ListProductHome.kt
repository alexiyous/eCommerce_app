package com.alexius.shopy.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexius.core.domain.model.Product

@Composable
fun ListProductHome(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onClick: (Product) -> Unit,
    isLoading: Boolean
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(count = products.size) {
            val product = products[it]
            ProductCardHome(product = product, onClick = onClick, isLoading = isLoading)
        }
    }
}