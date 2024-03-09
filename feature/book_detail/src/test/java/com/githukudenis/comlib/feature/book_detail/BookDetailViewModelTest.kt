package com.githukudenis.comlib.feature.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookDetailsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenreByIdUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeGenresRepository
import com.githukudenis.comlib.data.repository.fake.FakeMilestoneRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class BookDetailViewModelTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: BookDetailViewModel
    lateinit var getReadBooksUseCase: GetReadBooksUseCase
    lateinit var getAllBooksUseCase: GetAllBooksUseCase
    lateinit var getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase
    lateinit var getStreakUseCase: GetStreakUseCase
    lateinit var getGenreByIdUseCase: GetGenreByIdUseCase
    lateinit var getBookDetailUseCase: GetBookDetailsUseCase
    lateinit var toggleBookmarksUseCase: ToggleBookMarkUseCase

    @Before
    fun setUp() {
        getReadBooksUseCase = GetReadBooksUseCase(
            FakeUserPrefsRepository()
        )
        getAllBooksUseCase = GetAllBooksUseCase(
            FakeBooksRepository()
        )
        getBookmarkedBooksUseCase = GetBookmarkedBooksUseCase(
            FakeUserPrefsRepository()
        )
        getStreakUseCase = GetStreakUseCase(
            FakeMilestoneRepository()
        )
        toggleBookmarksUseCase = ToggleBookMarkUseCase(
            FakeUserPrefsRepository()
        )
        getGenreByIdUseCase = GetGenreByIdUseCase(
            FakeGenresRepository()
        )
        getBookDetailUseCase = GetBookDetailsUseCase(
            FakeBooksRepository()
        )
        viewModel = BookDetailViewModel(
            getBookmarkedBooksUseCase = getBookmarkedBooksUseCase,
            getBookDetailsUseCase = getBookDetailUseCase,
            getReadBooksUseCase = getReadBooksUseCase,
            getGenreByIdUseCase = getGenreByIdUseCase,
            toggleBookMarkUseCase = toggleBookmarksUseCase,
            savedStateHandle = SavedStateHandle(mapOf("bookId" to "1")),
        )
    }

    @Test
    fun testOnInitializeUpdatesState() = runTest {
        val state = viewModel.state.value as BookDetailUiState.Success
        assert(state.bookUiModel.authors.contains("Peter"))
    }

    @Test
    fun toggleBookmark() = runTest {
        viewModel.toggleBookmark("1")
        val state = viewModel.state.value as BookDetailUiState.Success

        assertTrue(!state.bookUiModel.isFavourite)
    }
}