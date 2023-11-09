package com.githukudenis.comlib.core.model.book

import kotlinx.serialization.Serializable

@Serializable
data class BooksData(
    val books: List<Book>
)