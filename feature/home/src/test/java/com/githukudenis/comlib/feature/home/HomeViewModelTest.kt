
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
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeMilestoneRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserRepository
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class HomeViewModelTest {

    @get:Rule val mainCoroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var homeViewModel: HomeViewModel
    lateinit var getReadBooksUseCase: GetReadBooksUseCase
    lateinit var getAllBooksUseCase: GetAllBooksUseCase
    lateinit var getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase
    lateinit var getStreakUseCase: GetStreakUseCase
    lateinit var getUserProfileUseCase: GetUserProfileUseCase
    lateinit var getUserPrefsUseCase: GetUserPrefsUseCase
    lateinit var toggleBookmarksUseCase: ToggleBookMarkUseCase

    @Before
    fun setup() {
        getReadBooksUseCase = GetReadBooksUseCase(FakeUserPrefsRepository())
        getAllBooksUseCase = GetAllBooksUseCase(FakeBooksRepository())
        getBookmarkedBooksUseCase = GetBookmarkedBooksUseCase(FakeUserPrefsRepository())
        getStreakUseCase = GetStreakUseCase(FakeMilestoneRepository())
        getUserProfileUseCase = GetUserProfileUseCase(FakeUserRepository())
        getUserPrefsUseCase = GetUserPrefsUseCase(FakeUserPrefsRepository())
        toggleBookmarksUseCase = ToggleBookMarkUseCase(FakeUserPrefsRepository())
        homeViewModel =
            HomeViewModel(
                getReadBooksUseCase = getReadBooksUseCase,
                getAllBooksUseCase = getAllBooksUseCase,
                getBookmarkedBooksUseCase = getBookmarkedBooksUseCase,
                getStreakUseCase = getStreakUseCase,
                getUserProfileUseCase = getUserProfileUseCase,
                toggleBookMarkUseCase = toggleBookmarksUseCase,
                getUserPrefsUseCase = getUserPrefsUseCase
            )
    }

    @After fun tearDown() {}

    @Test
    fun testStateInitialization() = runTest {
        assertEquals(homeViewModel.state.value.bookmarks.containsAll(setOf("1", "2", "3")), true)
    }

    @Test
    fun onToggleFavourite() = runTest {
        homeViewModel.onToggleFavourite("4")
        advanceUntilIdle()
        val bookMarks = homeViewModel.state.value.bookmarks
        assertContains(bookMarks, "4")
    }
}
