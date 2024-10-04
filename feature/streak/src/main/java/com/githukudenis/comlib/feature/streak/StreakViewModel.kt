
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
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.BookMilestone
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.data.repository.BooksRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
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
    private val userPrefsRepository: UserPrefsRepository,
    private val bookMilestoneRepository: BookMilestoneRepository,
    private val booksRepository: BooksRepository,
    private val savedStateHandle: SavedStateHandle
) : StatefulViewModel<StreakUiState>(StreakUiState()) {

    init {
        savedStateHandle.get<String>("bookId").also { getStreakDetails(it) }
        getAvailableBooks()
    }

    private fun getStreakDetails(bookId: String?) {
        if (bookId == null) return
        viewModelScope.launch {
            val bookMilestone = bookMilestoneRepository.bookMilestone.first()
            bookMilestone?.let { milestone ->
                update {
                    copy(
                        milestoneId = milestone.id,
                        selectedBook =
                            StreakBook(
                                id = milestone.bookId,
                                title = milestone.bookName,
                                pages = milestone.pages
                            ),
                        startDate = milestone.startDate?.toLocalDate()!!,
                        endDate = milestone.endDate?.toLocalDate()!!
                    )
                }
            }
        }
    }

    private fun getAvailableBooks() {
        viewModelScope.launch {
            update { copy(isLoading = true) }
            val readBooks = userPrefsRepository.userPrefs.mapLatest { it.readBooks }.first()
            val result = booksRepository.getAllBooks()

            when (result) {
                is ResponseResult.Failure -> {
                    update { copy(isLoading = false, error = result.error.message) }
                }
                is ResponseResult.Success -> {
                    update {
                        copy(
                            isLoading = false,
                            availableBooks = result.data.data.books.filterNot { it.id in readBooks }
                        )
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
                bookMilestoneRepository.insertBookMilestone(milestone)
            } else {
                bookMilestoneRepository.updateBookMilestone(milestone.copy(id = state.value.milestoneId))
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
