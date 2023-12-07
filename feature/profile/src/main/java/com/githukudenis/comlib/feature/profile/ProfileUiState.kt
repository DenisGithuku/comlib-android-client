package com.githukudenis.comlib.feature.profile

import com.githukudenis.comlib.core.model.user.User

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val error: String? = null,
    val isSignedOut: Boolean = false
)

data class Profile(
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val imageUrl: String? = null
)

fun User.toProfile(): Profile {
    return Profile(
        firstname = firstname,
        lastname = lastname,
        email = email,
        imageUrl = image
    )
}