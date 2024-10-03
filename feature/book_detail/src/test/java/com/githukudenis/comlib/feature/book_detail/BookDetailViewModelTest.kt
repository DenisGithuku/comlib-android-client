
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
package com.githukudenis.comlib.feature.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeGenresRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class BookDetailViewModelTest {

    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: BookDetailViewModel
    lateinit var userPrefsRepository: FakeUserPrefsRepository
    lateinit var booksRepository: FakeBooksRepository
    lateinit var genresRepository: FakeGenresRepository

    @Before
    fun setUp() {
        userPrefsRepository = FakeUserPrefsRepository()
        booksRepository = FakeBooksRepository()
        genresRepository = FakeGenresRepository()
        viewModel =
            BookDetailViewModel(
                userPreferencesRepository = userPrefsRepository,
                booksRepository = booksRepository,
                genresRepository = genresRepository,
                savedStateHandle = SavedStateHandle(mapOf("bookId" to "1"))
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
