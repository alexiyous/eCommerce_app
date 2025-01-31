package com.alexius.shopy.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alexius.core.domain.model.Product
import com.alexius.shopy.presentation.home.components.ListProductHome
import com.alexius.shopy.presentation.home.components.ProfileSection
import com.alexius.shopy.presentation.home.components.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onProfileSectionClicked: () -> Unit,
    imageProfile: String,
    nameProfile: String,
    isLoading: Boolean,
    featuredProducts: List<Product>,
    onProductClick: (Product) -> Unit,
    onFeatureProductSeeAllClick: () -> Unit,
    popularProducts: List<Product>,
    onPopularProductSeeAllClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 9.dp)
    ) {
        Spacer(modifier= modifier.height(20.dp))
        ProfileSection(
            imageUrl = imageProfile,
            name = nameProfile,
            isLoading = isLoading,
            onClick = onProfileSectionClicked
        )
        Spacer(modifier= modifier.height(22.dp))
        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = {}
        )
        Spacer(modifier= modifier.height(9.dp))
        Text(
            text = "Featured",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier= modifier.height(12.dp))
        ListProductHome(
            products = featuredProducts,
            onClick = onProductClick,
            isLoading = isLoading,
        )
        Spacer(modifier= modifier.height(11.dp))
        Text(
            modifier = modifier.align(Alignment.End).clickable(onClick = onFeatureProductSeeAllClick),
            text = "See All",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "Most Popular",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier= modifier.height(12.dp))
        ListProductHome(
            products = popularProducts,
            onClick = onProductClick,
            isLoading = isLoading,
        )
        Text(
            modifier = modifier.align(Alignment.End).clickable(onClick = onPopularProductSeeAllClick),
            text = "See All",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}