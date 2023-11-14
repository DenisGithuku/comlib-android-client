package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class SingleUserResponse(
    val `data`: Data,
    val status: String
)