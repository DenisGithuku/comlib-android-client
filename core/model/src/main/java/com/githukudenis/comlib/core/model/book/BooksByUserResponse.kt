package com.githukudenis.comlib.core.model.book

import kotlinx.serialization.Serializable

@Serializable class BooksByUserResponse(
    val data: BooksData,
    val requestedAt: String,
    val results: Int,
    val status: String
)
