
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
package com.githukudenis.comlib.feature.home

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeMilestoneRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserRepository
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class HomeViewModelTest {

    @get:Rule val mainCoroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userPrefsRepository: FakeUserPrefsRepository
    private lateinit var userRepository: FakeUserRepository
    private lateinit var booksRepository: FakeBooksRepository
    private lateinit var bookMilestoneRepository: FakeMilestoneRepository

    @Before
    fun setup() {
        userPrefsRepository = FakeUserPrefsRepository()
        userRepository = FakeUserRepository()
        booksRepository = FakeBooksRepository()
        bookMilestoneRepository = FakeMilestoneRepository()

        homeViewModel =
            HomeViewModel(
                userPrefsRepository = userPrefsRepository,
                booksRepository = booksRepository,
                bookMilestoneRepository = bookMilestoneRepository
            )
    }

    @After fun tearDown() {}

    @Test
    fun testStateInitialization() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.state.collect()
        }
        advanceUntilIdle()
        assertEquals(
            (homeViewModel.state.value.availableState as FetchItemState.Success).data.count {
                it.isFavourite
            },
            3
        )
    }

    @Test
    fun onToggleFavourite() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.state.collect()
        }
        homeViewModel.onToggleFavourite("4")
        advanceUntilIdle()
        assertContains(
            (homeViewModel.state.value.availableState as FetchItemState.Success).data.map { it.book.id },
            "4"
        )
    }

    @Test
    fun `test refresh page moves to next page`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.state.collect()
        }
        homeViewModel.onRefreshPage()
        advanceUntilIdle()
        assertEquals(homeViewModel.state.value.pagerState.second, 2)
    }

    @Test
    fun `test reserve book that does not exist returns error`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.state.collect()
        }
        homeViewModel.reserveBook("1000")
        advanceUntilIdle()
        assertEquals(homeViewModel.state.value.userMessages.first().message, "Book not found")
    }

    @Test
    fun `test reserve book returns success`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.state.collect()
        }
        homeViewModel.reserveBook("1")
        advanceUntilIdle()
        assertEquals(
            homeViewModel.state.value.userMessages.first().message,
            "Book reserved successfully"
        )
    }
}
