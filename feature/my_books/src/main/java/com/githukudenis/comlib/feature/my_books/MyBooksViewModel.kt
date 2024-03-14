package com.githukudenis.comlib.feature.my_books

import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetBooksByUserUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyBooksUiState(
    val isLoading: Boolean = false, val books: List<Book> = emptyList(), val error: String? = null
)

@HiltViewModel
class MyBooksViewModel @Inject constructor(
    private val getBooksByUserUseCase: GetBooksByUserUseCase,
    private val getUserPrefsUseCase: GetUserPrefsUseCase
) : StatefulViewModel<MyBooksUiState>(MyBooksUiState()) {


    init {
        viewModelScope.launch {
            getUserPrefsUseCase().collectLatest { prefs ->
                requireNotNull(prefs.userId).run {
                    getBooks(this)
                }
            }
        }
    }

    private suspend fun getBooks(userId: String) {
        when (val result = getBooksByUserUseCase(userId)) {
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