package com.githukudenis.comlib.core.model.genre

import kotlinx.serialization.Serializable

@Serializable
data class SingleGenreResponse(
    val `data`: Data,
    val status: String
)