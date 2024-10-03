
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
package com.githukudenis.comlib.feature.edit

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@MediumTest
class EditProfileViewModelTest {

    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: EditProfileViewModel
    lateinit var userRepository: FakeUserRepository
    lateinit var userPrefsRepository: FakeUserPrefsRepository

    @Before
    fun setUp() {
        userRepository = FakeUserRepository()
        userPrefsRepository = FakeUserPrefsRepository()
        viewModel =
            EditProfileViewModel(
                userPrefsRepository = userPrefsRepository,
                userRepository = userRepository
            )
    }

    @Test
    fun testStateInitialization() = runTest {
        advanceUntilIdle()
        assertEquals(viewModel.state.value.firstname, "5.firstname")
    }

    @Test
    fun testUpdateUser() = runTest {
        viewModel.update { copy(firstname = "test.firstname", lastname = "test.lastname") }
        viewModel.updateUser()
        assertTrue(
            userRepository.users.any {
                it.firstname == "test.firstname" && it.lastname == "test.lastname"
            }
        )
    }

    @Test
    fun testOChangeFirstname() = runTest {
        viewModel.onChangeFirstname("test.firstname")
        assertEquals(viewModel.state.value.firstname, "test.firstname")
    }

    @Test
    fun testOnChangeLastname() = runTest {
        viewModel.onChangeLastname("test.lastname")
        assertEquals(viewModel.state.value.lastname, "test.lastname")
    }

    @Test
    fun testOnChangeUsername() = runTest {
        viewModel.onChangeUsername("test.username")
        assertEquals(viewModel.state.value.username, "test.username")
    }
}
