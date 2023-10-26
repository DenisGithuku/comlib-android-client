package com.githukudenis.comlib.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val users: List<User>,
    val requestedAt: String,
    val results: Int,
    val status: String
)