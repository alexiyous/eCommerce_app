package com.alexius.shopy.presentation.home.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.valentinilk.shimmer.shimmer
import com.alexius.shopy.R

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(if (imageUrl.isNotEmpty()) imageUrl else R.drawable.baseline_person_24)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        onError = {
            Toast.makeText(context, "Failed to load image profile", Toast.LENGTH_SHORT).show()
        }
    )

    val loading = remember(isLoading, painter.state) {
        isLoading || imageUrl.isNotEmpty() && painter.state is AsyncImagePainter.State.Loading
    }

    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .size(48.dp)
                .then(if (loading) modifier.shimmer() else modifier)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable(onClick = onClick)
            ,
            painter = painter,
            contentDescription = null
        )
        Spacer(modifier = modifier.width(8.dp))
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Hello,",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}