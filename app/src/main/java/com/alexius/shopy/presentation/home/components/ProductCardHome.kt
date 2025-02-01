package com.alexius.shopy.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alexius.core.domain.model.Product
import com.alexius.shopy.presentation.ui.theme.ShopyTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
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

    Card(modifier = modifier
        .height(143.dp)
        .width(126.dp)
        .clickable(onClick = {
            onClick(product)
        }),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        )
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Image(
                modifier = modifier
                    .then(if (loading) modifier.shimmer(rememberShimmer(ShimmerBounds.View)) else modifier)
                    .size(height = 99.dp, width = 126.dp)
                    .background(MaterialTheme.colorScheme.onSurface),
                contentScale = ContentScale.Crop,
                painter = painter,
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ShopyTheme {
        ProductCardHome(
            product = Product(
                id = 1,
                title = "Product 1",
                description = "Description 1",
                price = 100.0,
                image = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.iconfinder.com%2Ficons%2F336072%2Fphoto_avatar_camera_photography_photos_pictures_profile_image_icon&psig=AOvVaw2ID23k8GdAzQHbixqD25C1&ust=1738394434778000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKCioIi2n4sDFQAAAAAdAAAAABAE",
                category = "Category 1"
            ),
            onClick = {},
            isLoading = true
        )
    }
}