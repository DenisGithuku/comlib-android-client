package com.githukudenis.comlib.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UploadUserResponse(
    val status: String,
    val message: String

)
