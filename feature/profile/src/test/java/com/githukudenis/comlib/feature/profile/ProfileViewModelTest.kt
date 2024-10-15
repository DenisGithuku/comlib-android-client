
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
package com.githukudenis.comlib.feature.profile

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.data.repository.AuthRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeAuthRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserRepository
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import junit.framework.TestCase.assertTrue
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class ProfileViewModelTest {

    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    private lateinit var userRepository: FakeUserRepository
    private lateinit var userPrefsRepository: FakeUserPrefsRepository
    private lateinit var viewModel: ProfileViewModel
    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        userRepository = FakeUserRepository()
        userPrefsRepository = FakeUserPrefsRepository()
        authRepository = FakeAuthRepository()
        viewModel =
            ProfileViewModel(
                userPrefsRepository = userPrefsRepository,
                userRepository = userRepository,
                authRepository = authRepository
            )
    }

    @Test
    fun testStateInitialization() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        advanceUntilIdle()
        assertEquals(viewModel.state.value.user.firstname, "5.firstname")
    }

    @Test
    fun testUpdateUser() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onChangeFirstname("test.firstname")
        viewModel.onChangeLastname("test.lastname")
        viewModel.updateUser()
        advanceUntilIdle()
        assertTrue(
            userRepository.users.any {
                it.firstname == "test.firstname" && it.lastname == "test.lastname"
            }
        )
    }

    @Test
    fun testOChangeFirstname() = runTest {
        viewModel.onChangeFirstname("test.firstname")
        assertEquals(viewModel.state.value.user.firstname, "test.firstname")
    }

    @Test
    fun testOnChangeLastname() = runTest {
        viewModel.onChangeLastname("test.lastname")
        println(viewModel.state.value.user.id)
        assertEquals(viewModel.state.value.user.lastname, "test.lastname")
    }

    @Test
    fun testOnChangeUsername() = runTest {
        viewModel.onChangeUsername("test.username")
        assertEquals(viewModel.state.value.user.username, "test.username")
    }

    @Test
    fun testResetUpdateStatus() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.updateUser()
        viewModel.onResetUpdateStatus()
        assertEquals(viewModel.state.value.isUpdateComplete, false)
    }
}
