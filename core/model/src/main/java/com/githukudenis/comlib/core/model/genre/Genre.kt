package com.githukudenis.comlib.core.model.genre

import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val _id: String,
    val id: String,
    val name: String
)