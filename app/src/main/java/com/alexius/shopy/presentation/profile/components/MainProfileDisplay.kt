package com.alexius.shopy.presentation.profile.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alexius.core.domain.model.UserInfoDomain
import com.alexius.shopy.R
import com.alexius.shopy.presentation.ui.theme.ShopyTheme
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.valentinilk.shimmer.shimmer

@Composable
fun MainProfileDisplay(
    modifier: Modifier = Modifier,
    userInfo: UserInfoDomain,
    onCropSuccess: (Bitmap) -> Unit,
    isUploading: Boolean,
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            imageUri = result.uriContent
        } else{
            val exception = result.error
        }
    }

    LaunchedEffect(imageUri) {
        if (imageUri != null){
            if (Build.VERSION.SDK_INT < 28){
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            //Upload the image to the server and pass the string URL to home screen
            onCropSuccess(bitmap!!)
            Log.d("MainProfileDisplay", "Image Uri: $imageUri")
        }
    }

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(if (userInfo.profileImage.isNotEmpty()) userInfo.profileImage else R.drawable.baseline_person_24)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop
    )

    val loading = remember(painter.state) {
       painter.state is AsyncImagePainter.State.Loading
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap?.asImageBitmap()!!,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = CircleShape
                        )
                        .align(Alignment.Center)
                )
                if (isUploading){
                    CircularProgressIndicator(
                        modifier = modifier.size(80.dp),
                    )
                }
            } else {
                Image(
                    modifier = modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .align(Alignment.Center)
                        .then(if (loading) modifier.shimmer() else modifier),
                    painter = painter,
                    contentDescription = null
                )
                if (isUploading){
                    CircularProgressIndicator(
                        modifier = modifier.size(80.dp),
                    )
                }
            }

            Icon(
                modifier = modifier.align(Alignment.BottomEnd)
                    .clickable(onClick = {
                        val cropOption = CropImageContractOptions(CropImage.CancelledResult.uriContent, CropImageOptions())
                        imageCropLauncher.launch(cropOption)
                    }),
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null,
                tint = Color.Gray
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ShopyTheme {
        MainProfileDisplay(
            userInfo = UserInfoDomain(
                profileImage = "",
                name = "Alexius",
                email = ""
            ),

            onCropSuccess = {},
            isUploading = false
        )
    }
}