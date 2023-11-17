package com.githukudenis.comlib.core.model.genre

import kotlinx.serialization.Serializable

@Serializable
data class AllGenresResponse(
    val `data`: DataX,
    val requestedAt: String,
    val results: Int,
    val status: String
)