package com.alexius.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoDomain(
    val email: String,
    val name: String,
    val profileImage: String
) : Parcelable
