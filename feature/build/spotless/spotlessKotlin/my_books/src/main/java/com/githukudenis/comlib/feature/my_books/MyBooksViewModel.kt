
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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn

data class MyBooksUiState(
    val isLoading: Boolean = false,
    val owned: List<Book> = emptyList(),
    val read: List<Book> = emptyList(),
    val favourite: List<Book> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class MyBooksViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _pagingData: MutableStateFlow<Pair<Int, Int>> = MutableStateFlow(Pair(1, 10))

    val state =
        combine(_pagingData, userPrefsRepository.userPrefs) { pagingData, userPrefs ->
                userPrefs.userId?.let { userId ->
                    val booksResult = booksRepository.getAllBooks(pagingData.first, pagingData.second)
                    val readBooksIds = userPrefsRepository.userPrefs.first().readBooks
                    val favouriteBooksIds = userPrefsRepository.userPrefs.first().bookmarkedBooks
                    when (booksResult) {
                        is ResponseResult.Failure -> {
                            MyBooksUiState(error = booksResult.error.message)
                        }
                        is ResponseResult.Success -> {
                            MyBooksUiState(
                                owned =
                                    booksResult.data.data.books
                                        .asSequence()
                                        .filter { it.owner == userId }
                                        .sortedBy { it.title }
                                        .toList(),
                                favourite =
                                    booksResult.data.data.books
                                        .asSequence()
                                        .filter { favouriteBooksIds.contains(it.id) }
                                        .sortedBy { it.title }
                                        .toList(),
                                read =
                                    booksResult.data.data.books
                                        .asSequence()
                                        .filter { readBooksIds.contains(it.id) }
                                        .sortedBy { it.title }
                                        .toList()
                            )
                        }
                    }
                } ?: MyBooksUiState()
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MyBooksUiState())
}
