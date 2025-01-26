package com.alexius.shopy.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alexius.shopy.R
import com.alexius.shopy.presentation.ui.theme.ShopyTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
    Box(
        modifier = modifier
            .fillMaxSize().background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.BottomCenter
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = modifier.fillMaxSize()
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ShopyTheme() {
        LoadingScreen()
    }
}