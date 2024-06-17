
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
package com.githukudenis.comlib.feature.streak

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.UpdateStreakUseCase
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.BookMilestone
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
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

data class StreakUiState(
    val isLoading: Boolean = false,
    val selectedBook: StreakBook? = null,
    val milestoneId: Long? = null,
    val saveSuccess: Boolean = false,
    val availableBooks: List<Book> = emptyList(),
    val startDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val endDate: LocalDate =
        Clock.System.todayIn(TimeZone.currentSystemDefault()).plus(7, DateTimeUnit.DAY),
    val error: String? = null
) {
    val isValid: Boolean = selectedBook != null
}

data class StreakBook(val id: String?, val title: String?, val pages: Int? = null)

fun Book.asStreakBook(): StreakBook = StreakBook(id, title, pages)

@HiltViewModel
class StreakViewModel
@Inject
constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getReadBooksUseCase: GetReadBooksUseCase,
    private val saveStreakUseCase: SaveStreakUseCase,
    private val getStreakUseCase: GetStreakUseCase,
    private val updateStreakUseCase: UpdateStreakUseCase,
    private val savedStateHandle: SavedStateHandle
) : StatefulViewModel<StreakUiState>(StreakUiState()) {

    init {
        savedStateHandle.get<String>("bookId").also { getStreakDetails(it) }
        getAvailableBooks()
    }

    private fun getStreakDetails(bookId: String?) {
        if (bookId == null) return
        viewModelScope.launch {
            val bookMilestone = getStreakUseCase().first()
            bookMilestone?.let { bookMilestone ->
                update {
                    copy(
                        milestoneId = bookMilestone.id,
                        selectedBook =
                            StreakBook(
                                id = bookMilestone.bookId,
                                title = bookMilestone.bookName,
                                pages = bookMilestone.pages
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
                        update { copy(isLoading = false, error = "No books available") }
                    }
                    is DataResult.Error -> update { copy(error = result.message, isLoading = false) }
                    is DataResult.Loading -> update { copy(isLoading = true) }
                    is DataResult.Success ->
                        update {
                            copy(isLoading = false, availableBooks = result.data.filterNot { it.id in readBooks })
                        }
                }
            }
        }
    }

    fun onToggleBook(streakBook: StreakBook?) {
        update { copy(selectedBook = streakBook) }
    }

    fun onSaveStreak() {
        viewModelScope.launch {
            val milestone =
                BookMilestone(
                    bookId = state.value.selectedBook?.id,
                    bookName = state.value.selectedBook?.title,
                    startDate =
                        state.value.startDate
                            .atStartOfDayIn(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds(),
                    endDate =
                        state.value.endDate
                            .atStartOfDayIn(TimeZone.currentSystemDefault())
                            .toEpochMilliseconds(),
                    pages = state.value.selectedBook?.pages
                )
            if (savedStateHandle.get<String>("bookId") == null) {
                saveStreakUseCase(milestone)
            } else {
                updateStreakUseCase(milestone.copy(id = state.value.milestoneId))
            }
            update { copy(saveSuccess = true) }
        }
    }

    fun onChangeStartDate(date: LocalDate) {
        update { copy(startDate = date) }
    }

    fun onChangeEndDate(date: LocalDate) {
        update { copy(endDate = date) }
    }
}
