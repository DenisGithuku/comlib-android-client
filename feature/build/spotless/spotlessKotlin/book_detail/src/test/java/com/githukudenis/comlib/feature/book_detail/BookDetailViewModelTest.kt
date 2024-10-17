
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
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeGenresRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import junit.framework.TestCase.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        advanceUntilIdle()
        val state = viewModel.state.value as BookDetailUiState.Success
        assert(state.bookUiModel.authors.contains("Peter"))
    }

    @Test
    fun toggleBookmark() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.toggleBookmark("1")
        advanceUntilIdle()
        val state = viewModel.state.value as BookDetailUiState.Success

        assertTrue(!state.bookUiModel.isFavourite)
    }

    @Test
    fun `test reserve book that does not exist returns error`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onReserveBook("1000")
        advanceUntilIdle()
        val state = viewModel.state.value as BookDetailUiState.Success
        assertEquals(state.userMessages.first().message, "Book not found")
    }

    @Test
    fun `test reserve book returns success`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onReserveBook("1")
        advanceUntilIdle()
        val state = viewModel.state.value as BookDetailUiState.Success

        assertEquals(state.userMessages.first().message, "Book reserved successfully")
    }

    @Test
    fun `test unreserve book that does not exist returns error`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onUnReserveBook("1000")
        advanceUntilIdle()
        val state = viewModel.state.value as BookDetailUiState.Success
        assertEquals(state.userMessages.first().message, "Book not found")
    }

    @Test
    fun `test unreserve book returns success`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onReserveBook("1")
        advanceUntilIdle()
        val state = viewModel.state.value as BookDetailUiState.Success
        assertTrue(state.bookUiModel.isReserved)

        viewModel.onUnReserveBook("1")
        advanceUntilIdle()
        assertFalse((viewModel.state.value as BookDetailUiState.Success).bookUiModel.isReserved)
    }

    @Test
    fun `test add user message`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onShowUserMessage(
            UserMessage(id = 1, message = "Test", messageType = MessageType.INFO)
        )
        advanceUntilIdle()
        val state = viewModel.state.value as BookDetailUiState.Success
        assertEquals(state.userMessages.first().message, "Test")
        assertEquals(state.userMessages.first().messageType, MessageType.INFO)
    }

    @Test
    fun `test remove user message`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onShowUserMessage(
            UserMessage(id = 1, message = "Test", messageType = MessageType.INFO)
        )
        viewModel.onUserMessageShown(1)
        val state = viewModel.state.value as BookDetailUiState.Success
        assertEquals(state.userMessages.count(), 0)
    }
}
