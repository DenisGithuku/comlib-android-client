package com.githukudenis.comlib.feature.streak

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.BookMilestone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import javax.inject.Inject

data class StreakUiState(
    val isLoading: Boolean = false,
    val selectedBook: StreakBook? = null,
    val saveSuccess: Boolean = false,
    val availableBooks: List<Book> = emptyList(),
    val startDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val endDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        .plus(7, DateTimeUnit.DAY),
    val error: String? = null,
) {
    val isValid: Boolean = selectedBook != null
}

data class StreakBook(
    val id: String?, val title: String?, val pages: Int? = null
)

fun Book.asStreakBook(): StreakBook = StreakBook(id, title, pages)

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getReadBooksUseCase: GetReadBooksUseCase,
    private val saveStreakUseCase: SaveStreakUseCase,
    private val getStreakUseCase: GetStreakUseCase,
    savedStateHandle: SavedStateHandle
) : StatefulViewModel<StreakUiState>(StreakUiState()) {

    init {
        savedStateHandle.get<String>("bookId").run {
            getStreakDetails(this)
        }
        getAvailableBooks()
    }

    private fun getStreakDetails(bookId: String?) {
        if (bookId == null) return
        viewModelScope.launch {
            val bookMilestone = getStreakUseCase().first()
            bookMilestone?.let { bookMilestone ->
                update {
                    copy(
                        selectedBook = StreakBook(
                            id = bookMilestone.bookId,
                            title = bookMilestone.bookName
                        ),
                        startDate = bookMilestone.startDate?.toLocalDate()!!,
                        endDate = bookMilestone.endDate?.toLocalDate()!!
                    )
                }
            }
        }
    }

    private fun getAvailableBooks() {
        viewModelScope.launch {
            val readBooks = getReadBooksUseCase().first()
            getAllBooksUseCase().collectLatest { result ->
                when (result) {
                    DataResult.Empty -> {
                        update { copy(isLoading = false) }
                    }
                    is DataResult.Error -> update {
                        copy(
                            error = result.message
                        )
                    }
                    is DataResult.Loading -> update { copy(isLoading = true) }
                    is DataResult.Success -> update { copy(availableBooks = result.data.filterNot { it.id in readBooks }) }
                }
            }
        }
    }

    fun onToggleBook(streakBook: StreakBook?) {
        update { copy(selectedBook = streakBook) }
    }

    fun onSaveStreak() {
        viewModelScope.launch {
            val milestone = BookMilestone(
                bookId = state.value.selectedBook?.id,
                bookName = state.value.selectedBook?.title,
                startDate = state.value.startDate.atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
                endDate = state.value.endDate.atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds(),
                pages = state.value.selectedBook?.pages
            )

            saveStreakUseCase(milestone)
            update {
                copy(saveSuccess = true)
            }
        }
    }

    fun onChangeStartDate(date: LocalDate) {
        update { copy(startDate = date) }
    }

    fun onChangeEndDate(date: LocalDate) {
        update { copy(endDate = date) }
    }
}