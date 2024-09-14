
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
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetBooksByUserUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
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
    private val getBooksByUserUseCase: GetBooksByUserUseCase,
    private val getUserPrefsUseCase: GetUserPrefsUseCase
) : StatefulViewModel<MyBooksUiState>(MyBooksUiState()) {

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            getUserPrefsUseCase().collectLatest { prefs ->
                requireNotNull(prefs.userId).run {
                    when (val result = getBooksByUserUseCase(this)) {
                        DataResult.Empty -> {
                            update { copy(books = emptyList(), isLoading = false) }
                        }
                        is DataResult.Error -> {
                            update { copy(isLoading = false, error = result.message) }
                        }
                        is DataResult.Loading -> {
                            update { copy(isLoading = true) }
                        }
                        is DataResult.Success -> {
                            update { copy(isLoading = false, books = result.data) }
                        }
                    }
                }
            }
        }
    }

    fun onRetry() {
        getBooks()
    }
}
