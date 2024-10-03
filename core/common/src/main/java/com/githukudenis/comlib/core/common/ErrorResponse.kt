package com.githukudenis.comlib.core.common

import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse(
    val status: String,
    val message: String
)
