
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
import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeMilestoneRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import junit.framework.TestCase.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class StreakViewModelTest {

    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: StreakViewModel
    lateinit var booksRepository: FakeBooksRepository
    lateinit var milestoneRepository: FakeMilestoneRepository
    lateinit var userPrefsRepository: FakeUserPrefsRepository

    @Before
    fun setUp() {
        booksRepository = FakeBooksRepository()
        milestoneRepository = FakeMilestoneRepository()
        userPrefsRepository = FakeUserPrefsRepository()

        viewModel =
            StreakViewModel(
                savedStateHandle = SavedStateHandle(mapOf("bookId" to "1")),
                bookMilestoneRepository = milestoneRepository,
                userPrefsRepository = userPrefsRepository,
                booksRepository = booksRepository
            )
    }

    @Test
    fun testStateInitialization() = runTest {
        advanceUntilIdle()
        assertEquals(viewModel.state.value.selectedBook?.title, "testBook")
    }

    @Test
    fun onToggleBook() = runTest {
        viewModel.onToggleBook(StreakBook(id = "testId", title = "testTitle", pages = 100))
        assertEquals(viewModel.state.value.selectedBook?.pages, 100)
    }

    @Test
    fun onSaveStreak() = runTest {
        viewModel.onSaveStreak()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.saveSuccess)
    }

    @Test
    fun onChangeStartDate() = runTest {
        viewModel.onChangeStartDate(LocalDate(2024, 3, 17))
        assertEquals(viewModel.state.value.startDate, LocalDate(2024, 3, 17))
    }

    @Test
    fun onChangeEndDate() = runTest {
        viewModel.onChangeEndDate(LocalDate(2024, 3, 17))
        assertEquals(viewModel.state.value.endDate, LocalDate(2024, 3, 17))
    }
}
