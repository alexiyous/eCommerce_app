package com.alexius.shopy.presentation.profile

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.shopy.presentation.profile.components.MainProfileDisplay

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userInfo: UserInfoDomain,
    onCropSuccess: (Bitmap) -> Unit,
    isLoading: Boolean,
    onUploadingNewProfilePic: (Boolean) -> Unit
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
        )
    }
}