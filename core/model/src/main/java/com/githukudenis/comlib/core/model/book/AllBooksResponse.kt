package com.githukudenis.comlib.core.model.book

import kotlinx.serialization.Serializable

@Serializable
data class AllBooksResponse(
    val data: BooksData,
    val requestedAt: String,
    val results: Int,
    val status: String
)