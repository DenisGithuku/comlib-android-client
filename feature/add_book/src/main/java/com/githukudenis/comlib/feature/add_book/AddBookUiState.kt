
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.feature.add_book

import android.net.Uri
import com.githukudenis.comlib.core.model.genre.Genre

sealed class GenreUiState {
    data object Loading : GenreUiState()

    data class Success(val genres: List<Genre>) : GenreUiState()

    data class Error(val message: String) : GenreUiState()
}

data class AddBookUiState(
    val photoUri: Uri? = null,
    val title: String = "",
    val authors: String = "",
    val edition: String = "",
    val pages: String = "",
    val description: String = "",
    val genreState: GenreUiState = GenreUiState.Loading,
    val message: String = "",
    val selectedGenre: Genre = Genre(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
) {
    val descriptionIsValid: Boolean = description.isNotEmpty() && description.length >= 200

    val uiIsValid: Boolean =
        title.isNotEmpty() &&
            photoUri != null &&
            authors.isNotEmpty() &&
            edition.isNotEmpty() &&
            pages.isNotEmpty() &&
            descriptionIsValid &&
            selectedGenre.id.isNotEmpty()
}

sealed class AddBookUiEvent {
    data class OnTitleChange(val newValue: String) : AddBookUiEvent()

    data class OnGenreChange(val newValue: Genre) : AddBookUiEvent()

    data class OnAuthorChange(val newValue: String) : AddBookUiEvent()

    data class OnEditionChange(val newValue: String) : AddBookUiEvent()

    data class OnPageChange(val newValue: String) : AddBookUiEvent()

    data class OnDescriptionChange(val newValue: String) : AddBookUiEvent()

    data class OnChangePhoto(val uri: Uri) : AddBookUiEvent()

    data object OnSave : AddBookUiEvent()

    data class ShowMessage(val message: String) : AddBookUiEvent()

    data object DismissMessage : AddBookUiEvent()

    data object OnRetryLoadGenres : AddBookUiEvent()
}
