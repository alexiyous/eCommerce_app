package com.alexius.core.data.model.remote

import com.alexius.core.domain.model.UserInfoDomain

data class UserInfoFirestore(
    val email: String? = null,
    val name: String? = null,
    val profileImage: String? = null
)

fun UserInfoFirestore.toDomainModel(): UserInfoDomain {
    return UserInfoDomain(
        email = this.email?:"",
        name = this.name?: "",
        profileImage = this.profileImage?: ""
    )
}
