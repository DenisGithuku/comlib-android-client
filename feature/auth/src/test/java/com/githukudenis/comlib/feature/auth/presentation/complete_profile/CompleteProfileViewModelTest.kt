
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
package com.githukudenis.comlib.feature.auth.presentation.complete_profile

import android.net.Uri
import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserRepository
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

@MediumTest
class CompleteProfileViewModelTest {

    @get:Rule val mainCoroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    private lateinit var viewModel: CompleteProfileViewModel
    private lateinit var userRepository: FakeUserRepository
    private lateinit var userPrefsRepository: FakeUserPrefsRepository

    @Before
    fun setUp() {
        userRepository = FakeUserRepository()
        userPrefsRepository = FakeUserPrefsRepository()
        viewModel = CompleteProfileViewModel(userRepository, userPrefsRepository)
    }

    @Test
    fun onSelectUserName() = runTest {
        viewModel.onSelectUserName("test")
        assert(viewModel.state.value.userName == "test")
    }

    @Test
    fun onDismissUserMessage() = runTest {
        viewModel.onDismissUserMessage()
        assert(viewModel.state.value.userMessages.isEmpty())
    }

    @Test
    fun onSubmit() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onSelectUserName("test")

        // create a Uri mock
        val mockUri = Mockito.mock(Uri::class.java)
        viewModel.onSelectImage(mockUri)
        viewModel.onSubmit()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.isSuccess)
    }
}
