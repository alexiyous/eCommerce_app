package com.alexius.shopy.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.shopy.R
import com.alexius.shopy.presentation.commons.MainButton
import com.alexius.shopy.presentation.ui.theme.ShopyTheme

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(enabled = true, state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(196.dp))

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_onboarding),
                contentDescription = null,
                modifier = modifier.size(274.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(38.dp))

            Text(
                text = "Welcome to Shopy, where desires meet expectations",
                modifier = modifier.padding(horizontal = 45.dp),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(12.dp))

            Text(
                text = "Shop for the best products at the best prices from the comfort of your home or office",
                modifier = modifier.padding(horizontal = 45.dp),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(37.dp))

            MainButton(
                text = "Sign In",
                onClick = onSignInClick,
                enabled = true,
            )

            Spacer(modifier = modifier
                .fillMaxWidth()
                .height(10.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Text(
                    text = "Don't have account yet??",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = " Sign Up",
                    modifier = modifier.clickable(onClick = onSignUpClick),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ShopyTheme() {
        OnboardingScreen(
            onSignInClick = {},
            onSignUpClick = {}
        )
    }
}