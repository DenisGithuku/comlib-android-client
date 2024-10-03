
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
package com.githukudenis.comlib.feature.books

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeGenresRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

@MediumTest
class BooksViewModelTest {

    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: BooksViewModel
    lateinit var userPrefsRepository: FakeUserPrefsRepository
    lateinit var booksRepository: FakeBooksRepository
    lateinit var genresRepository: FakeGenresRepository

    @Before
    fun setUp() {
        userPrefsRepository = FakeUserPrefsRepository()
        booksRepository = FakeBooksRepository()
        genresRepository = FakeGenresRepository()

        viewModel =
            BooksViewModel(
                userPrefsRepository = userPrefsRepository,
                booksRepository = booksRepository,
                genresRepository = genresRepository
            )
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
                        assertEquals(bookState.books.first().authors, listOf("Sam", "Peter", "Charlie"))
                    }
                }
            }
        }
    }

    @Test
    fun onChangeGenre() {
        viewModel.onChangeGenre("5")
        val state = viewModel.uiState.value
        when (state) {
            is BooksUiState.Error -> Unit
            BooksUiState.Loading -> Unit
            is BooksUiState.Success -> {
                assertContains(state.selectedGenres.map { it.id }, "5")
            }
        }
    }
}
