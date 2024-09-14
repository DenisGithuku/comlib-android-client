
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
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.UpdateStreakUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeMilestoneRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
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
    lateinit var getAllBooksUseCase: GetAllBooksUseCase
    lateinit var getReadBooksUseCase: GetReadBooksUseCase
    lateinit var saveStreakUseCase: SaveStreakUseCase
    lateinit var getStreakUseCase: GetStreakUseCase
    lateinit var updateStreakUseCase: UpdateStreakUseCase

    @Before
    fun setUp() {
        getAllBooksUseCase = GetAllBooksUseCase(FakeBooksRepository())
        getReadBooksUseCase = GetReadBooksUseCase(FakeUserPrefsRepository())
        saveStreakUseCase = SaveStreakUseCase(FakeMilestoneRepository())
        getStreakUseCase = GetStreakUseCase(FakeMilestoneRepository())
        updateStreakUseCase = UpdateStreakUseCase(FakeMilestoneRepository())
        viewModel =
            StreakViewModel(
                getAllBooksUseCase = getAllBooksUseCase,
                getReadBooksUseCase = getReadBooksUseCase,
                saveStreakUseCase = saveStreakUseCase,
                getStreakUseCase = getStreakUseCase,
                savedStateHandle = SavedStateHandle(mapOf("bookId" to "1")),
                updateStreakUseCase = updateStreakUseCase
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
