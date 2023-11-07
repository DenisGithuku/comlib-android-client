package com.githukudenis.comlib.feature.home

import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.user.User

sealed class HomeUiState {
    data class Success(
        val booksState: BooksState = BooksState.Loading,
        val user: User? = null,
        val timePeriod: TimePeriod = TimePeriod.MORNING,
        val userMessages: List<UserMessage> = emptyList(),
        val userProfileState: UserProfileState = UserProfileState.Loading
    ) : HomeUiState()

    data object Loading : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

sealed class UserProfileState {
    data object Loading : UserProfileState()
    data class Error(val message: String) : UserProfileState()
    data class Success(
        val user: User?
    ) : UserProfileState()
}

sealed interface BooksState {
    data object Loading : BooksState
    data class Success(
        val available: List<Book> = emptyList(),
        val readBooks: List<Book> = emptyList(),
        val bookmarkedBooks: List<Book> = emptyList()
    ) : BooksState

    data class Error(val message: String) : BooksState
    data object Empty : BooksState

}
