package com.githukudenis.comlib.feature.streak

import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase
import com.githukudenis.comlib.core.model.book.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import javax.inject.Inject

data class StreakUiState(
    val isLoading: Boolean = false,
    val selectedBook: Book? = null,
    val availableBooks: List<Book> = emptyList(),
    val startDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val endDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).plus(7, DateTimeUnit.DAY),
    val error: String? = null,
) {
    val isValid: Boolean = selectedBook != null

}

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getReadBooksUseCase: GetReadBooksUseCase,
    private val streakUseCase: SaveStreakUseCase
): StatefulViewModel<StreakUiState>(StreakUiState()){

    init {
       getAvailableBooks()
    }

    private fun getAvailableBooks() {
        viewModelScope.launch {
            val readBooks = getReadBooksUseCase().first()
            getAllBooksUseCase().collectLatest { result ->
                val state = when(result) {
                    DataResult.Empty -> StreakUiState(isLoading = false)
                    is DataResult.Error -> StreakUiState(error = result.message)
                    is DataResult.Loading -> StreakUiState(isLoading = true)
                    is DataResult.Success -> StreakUiState(availableBooks = result.data.filterNot { it.id in readBooks })
                }
                update { state }
            }
        }
    }

    fun onSelectBook(book: Book) {
        update { copy(selectedBook = book) }
    }

    fun onDeleteBook() {
        update { copy(selectedBook = null) }
    }
}