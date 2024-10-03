package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDTO(
    val email: String,
    val password: String
)