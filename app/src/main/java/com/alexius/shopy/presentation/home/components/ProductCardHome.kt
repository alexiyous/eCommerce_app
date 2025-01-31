package com.alexius.shopy.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alexius.core.domain.model.Product
import com.valentinilk.shimmer.shimmer

@Composable
fun ProductCardHome(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (Product) -> Unit,
    isLoading: Boolean
) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(product.image)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop
    )

    val loading = remember(isLoading, painter.state) {
        isLoading || painter.state is AsyncImagePainter.State.Loading
    }

    Card(modifier = modifier.height(143.dp).width(126.dp)
        .clickable(onClick = {
            onClick(product)
        }),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = modifier
                    .size(height = 99.dp, width = 126.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .then(if (loading) modifier.shimmer() else modifier),
                contentScale = ContentScale.Crop,
                model = painter,
                contentDescription = null
            )
            Column(
                modifier = modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }
    }
}