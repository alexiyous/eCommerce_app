package com.alexius.shopy.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.alexius.shopy.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

val displayFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Poppins"),
        fontProvider = provider,
    )
)

// Default Material 3 typography values
val baseline = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    //57/64
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    //45/52
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    //34/44
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    //32/40
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    //28/36
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    //24/32
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    //22/28
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    //16/24
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    //14/20
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    //16/24
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    //14/20
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    //12/16
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    //14/20
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    //12/16
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    //11/16
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)