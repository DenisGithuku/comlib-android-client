
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
package com.githukudenis.comlib.feature.auth.presentation.reset

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.data.repository.fake.FakeAuthRepository
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class ResetPasswordViewModelTest {

    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: ResetPasswordViewModel
    lateinit var authRepository: FakeAuthRepository

    @Before
    fun setUp() {
        authRepository = FakeAuthRepository()
        viewModel = ResetPasswordViewModel(authRepository)
    }

    @Test
    fun onEmailChange() = runTest {
        viewModel.onEmailChange("newmail@mail.com")
        assertEquals(viewModel.state.value.email, "newmail@mail.com")
    }

    @Test
    fun onResetWithWrongDetailsReturnsError() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onEmailChange("wrong.test.mail@mail.com")
        viewModel.onReset()
        advanceUntilIdle()
        assertEquals(viewModel.state.value.error?.message, "Couldn't find that user!")
    }

    @Test
    fun onResetWithValidEmailReturnsSuccess() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onEmailChange("william.henry.moody@my-own-personal-domain.com")
        viewModel.onReset()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.isSuccess)
    }

    @Test
    fun onDismissError() = runTest {
        viewModel.onEmailChange("wrong.test.mail@mail.com")
        viewModel.onReset()
        viewModel.onErrorShown()
        assertNull(viewModel.state.value.error)
    }
}
