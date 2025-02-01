package com.alexius.shopy.presentation.profile

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.shopy.presentation.main_navigation.components.ProfileOption
import com.alexius.shopy.presentation.profile.components.MainProfileDisplay

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userInfo: UserInfoDomain,
    onCropSuccess: (Bitmap) -> Unit,
    isLoading: Boolean,
    onUploadingNewProfilePic: (Boolean) -> Unit,
    onProfileNameChange: (String) -> Unit,
    onNameSubmit: (() -> Unit, (String) -> Unit) -> Unit,
    onSettingOptionClick: () -> Unit,
    onShareOptionClick: () -> Unit,
    onSignOut: () -> Unit
) {

    LaunchedEffect(isLoading) {
        onUploadingNewProfilePic(isLoading)
    }

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        MainProfileDisplay(
            userInfo = userInfo,
            onCropSuccess = onCropSuccess,
            onProfileNameChange = onProfileNameChange,
            onNameSubmit = onNameSubmit,
        )
        Spacer(modifier = modifier.height(24.dp))
        ProfileOption(
            icon = Icons.Default.Settings,
            title = "Setting",
            onClick = onSettingOptionClick
        )
        Spacer(modifier = modifier.height(16.dp))
        ProfileOption(
            icon = Icons.Default.Email,
            title = "Contact",
            onClick = {}
        )
        Spacer(modifier = modifier.height(16.dp))
        ProfileOption(
            icon = Icons.Default.Share,
            title = "Share App",
            onClick = onShareOptionClick
        )
        Spacer(modifier = modifier.height(16.dp))
        ProfileOption(
            icon = Icons.AutoMirrored.Filled.Help,
            title = "Help",
            onClick = onSettingOptionClick
        )
        Spacer(modifier = modifier.height(141.dp))
        Text(
            text = "Sign Out",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.clickable(onClick = onSignOut)
        )
    }
}