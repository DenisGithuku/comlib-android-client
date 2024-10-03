package com.githukudenis.comlib.core.model.book

import kotlinx.serialization.Serializable

@Serializable data class AddBookResponse(
    val status: String,
    val message: String,
)
