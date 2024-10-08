
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
package com.githukudenis.comlib.feature.my_books

import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class MyBooksUiState(
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class MyBooksViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val booksRepository: BooksRepository
) : StatefulViewModel<MyBooksUiState>(MyBooksUiState()) {

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            update { copy(isLoading = true) }
            val books = booksRepository.getAllBooks()
            val userId = userPrefsRepository.userPrefs.first().userId
            when (books) {
                is ResponseResult.Failure -> {
                    update { copy(isLoading = false, error = books.error.message) }
                }
                is ResponseResult.Success -> {
                    update {
                        copy(
                            isLoading = false,
                            books = books.data.data.books.filter { it.owner == userId }.sortedBy { it.title }
                        )
                    }
                }
            }
        }
    }

    fun onRetry() {
        getBooks()
    }
}
