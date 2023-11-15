package com.githukudenis.comlib.feature.book_detail

import com.githukudenis.comlib.core.model.book.Book

sealed class BookDetailUiState {
    data object Loading : BookDetailUiState()
    data class Success(
        val bookUiModel: BookUiModel
    ) : BookDetailUiState()

    data class Error(val message: String) : BookDetailUiState()
}

data class BookUiModel(
    val id: String,
    val title: String,
    val authors: List<String>,
    val genreIds: List<String>,
    val description: String,
    val imageUrl: String,
    val reservedBy: List<String>,
    val isRead: Boolean,
    val isFavourite: Boolean
)

fun Book.toBookUiModel(
    isFavourite: Boolean, isRead: Boolean
): BookUiModel {
    return BookUiModel(
        id = id,
        title = title,
        authors = authors,
        genreIds = genre_ids,
        description = description,
        imageUrl = image,
        isFavourite = isFavourite,
        isRead = isRead,
        reservedBy = reserved
    )
}