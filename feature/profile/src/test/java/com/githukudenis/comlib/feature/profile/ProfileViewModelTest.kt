
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
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.SignOutUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.UserRepository
import com.githukudenis.comlib.data.repository.fake.FakeAuthRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class ProfileViewModelTest {
    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: ProfileViewModel
    lateinit var getUserPrefsUseCase: GetUserPrefsUseCase
    lateinit var getUserProfileUseCase: GetUserProfileUseCase
    lateinit var signOutUseCase: SignOutUseCase
    lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = FakeUserRepository()
        getUserPrefsUseCase = GetUserPrefsUseCase(FakeUserPrefsRepository())
        getUserProfileUseCase = GetUserProfileUseCase(userRepository)
        signOutUseCase = SignOutUseCase(FakeAuthRepository())

        viewModel =
            ProfileViewModel(getUserPrefsUseCase, getUserProfileUseCase, signOutUseCase, userRepository)
    }

    @Test
    fun testStateInitialization() = runTest {
        val state = viewModel.uiState.value
        assert(state.profile?.email == "5@gmail.com")
    }

    @Test
    fun onSignOut() = runTest {
        viewModel.onSignOut()
        assertTrue(viewModel.uiState.value.isSignedOut)
    }

    @Test
    fun onToggleCache() = runTest {
        viewModel.onToggleCache(true)
        assertTrue(viewModel.uiState.value.isClearCache)
    }

    @Test
    fun onToggleSignOut() = runTest {
        viewModel.onToggleSignOut(true)
        assertTrue(viewModel.uiState.value.isSignout)
    }
}
