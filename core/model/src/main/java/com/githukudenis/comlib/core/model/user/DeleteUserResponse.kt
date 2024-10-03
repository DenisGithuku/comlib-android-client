package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable data class DeleteUserResponse(
    val status: String,
    val message: String
)