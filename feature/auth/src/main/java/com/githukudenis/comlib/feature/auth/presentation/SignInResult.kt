package com.githukudenis.comlib.feature.auth.presentation


data class SignInResult(
    val userData: UserData?,
    val errorMessage: String?
)

data class UserData(
    val id: String?,
    val username: String?,
    val email: String?,
    val profilePictureUrl: String?
)