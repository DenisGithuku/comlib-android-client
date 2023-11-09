package com.githukudenis.comlib.feature.auth.presentation


data class SignInResult(
    val userData: UserData?,
    val errorMessage: String?
)

data class UserData(
    val username: String?,
    val email: String?,
    val profilePictureUrl: String?,
    val authId: String? = null
)