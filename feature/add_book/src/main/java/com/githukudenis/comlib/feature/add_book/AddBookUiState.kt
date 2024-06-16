package com.githukudenis.comlib.feature.add_book

import android.net.Uri

data class AddBookUiState(
    val photoUri: Uri? = null,
    val title: String = "",
    val genre: String = "",
    val author: String = "",
    val edition: String = "",
    val year: String = "",
    val description: String = ""
) {
    val descriptionIsValid: Boolean = description.isNotEmpty() &&
            description.length >= 200

    val uiIsValid: Boolean = title.isNotEmpty() &&
            photoUri != null &&
            genre.isNotEmpty() &&
            author.isNotEmpty() &&
            edition.isNotEmpty() &&
            year.isNotEmpty() &&
            descriptionIsValid
}

sealed class AddBookUiEvent {
    data class OnTitleChange(val newValue: String): AddBookUiEvent()
    data class OnGenreChange(val newValue: String): AddBookUiEvent()
    data class OnAuthorChange(val newValue: String): AddBookUiEvent()
    data class OnEditionChange(val newValue: String): AddBookUiEvent()
    data class OnYearChange(val newValue: String): AddBookUiEvent()
    data class OnDescriptionChange(val newValue: String): AddBookUiEvent()
    data class OnChangePhoto(val uri: Uri): AddBookUiEvent()
    data object OnSave: AddBookUiEvent()
}
