package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class DeactivateUserResponse(
    val status: String,
    val message: String
)
