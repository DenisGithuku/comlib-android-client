package com.githukudenis.comlib.feature.books

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenresUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeGenresRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

@MediumTest
class BooksViewModelTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: BooksViewModel
    lateinit var getAllBooksUseCase: GetAllBooksUseCase
    lateinit var getGenresUseCase: GetGenresUseCase

    @Before
    fun setUp() {
        getAllBooksUseCase = GetAllBooksUseCase(
            FakeBooksRepository()
        )
        getGenresUseCase = GetGenresUseCase(
            FakeGenresRepository()
        )
        viewModel = BooksViewModel(getGenresUseCase, getAllBooksUseCase)
    }

    @Test
    fun testStateInitialization() = runTest {
        when (val state = viewModel.uiState.value) {
            is BooksUiState.Error -> {
                Unit
            }

            BooksUiState.Loading -> {
                Unit
            }

            is BooksUiState.Success -> {
                when (val bookState = state.bookListUiState) {
                    BookListUiState.Empty -> Unit
                    is BookListUiState.Error -> Unit
                    BookListUiState.Loading -> Unit
                    is BookListUiState.Success -> {
                        assertEquals(

                            bookState.books.first().authors, listOf("Sam", "Peter", "Charlie")
                        )
                    }
                }
            }
        }

    }

    @Test
    fun onChangeGenre() {
        viewModel.onChangeGenre(
            "5"
        )
        val state = viewModel.uiState.value
        when (state) {
            is BooksUiState.Error -> Unit
            BooksUiState.Loading -> Unit
            is BooksUiState.Success -> {
                assertContains(
                    state.selectedGenres.map { it.id }, "5"
                )
            }
        }

    }
}