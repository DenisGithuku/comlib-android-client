package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class AddUserResponse(
    val id: String,
    val status: String
)