package com.githukudenis.comlib.core.model.book

import kotlinx.serialization.Serializable

@Serializable
data class SingleBookResponse(
    val `data`: Data,
    val status: String
)