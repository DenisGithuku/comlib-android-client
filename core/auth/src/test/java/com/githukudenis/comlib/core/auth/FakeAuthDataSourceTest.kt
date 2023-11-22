package com.githukudenis.comlib.core.auth

import androidx.test.filters.SmallTest
import com.githukudenis.comlib.core.auth.di.FakeAuthDataSource
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.UserAuthData
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@SmallTest
class FakeAuthDataSourceTest {
    private val fakeAuthDataSource = FakeAuthDataSource()

    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Test
    fun `signUpWithEmail() with existing email returns failure`() = testScope.runTest {
        val userAuthData = UserAuthData(
            email = "alice.doe@example-pet-store.com",
            password = "password",
            age = 40,
            firstname = "Alice",
            lastname = "Doe"
        )
        val result = fakeAuthDataSource.signUpWithEmail(userAuthData)
        assertTrue(result is ResponseResult.Failure)
    }

    @Test
    fun `signUpWithEmail() with new details returns success`() = testScope.runTest {
        val userAuthData = UserAuthData(
            email = "sabrina.doe@example-pet-store.com",
            password = "password",
            age = 40,
            firstname = "Sabrina",
            lastname = "Doe"
        )
        val result = fakeAuthDataSource.signUpWithEmail(userAuthData)
        assertTrue(result is ResponseResult.Success)
        assertEquals("userId", (result as ResponseResult.Success).data)
    }

    @Test
    fun `login() with valid details calls onSuccess`() = testScope.runTest {
        val email = "alice.doe@example-pet-store.com"
        val password = "password"
        fakeAuthDataSource.login(email, password, onSuccess = {
            assertEquals(it, "userId")
        }, onError = {
            assertEquals(it, null)
        })
        advanceTimeBy(1000)
    }

    @Test
    fun `login() with invalid details calls onError`() = testScope.runTest {
        val email = "alice.doe@example.com"
        val password = "password"
        fakeAuthDataSource.login(email, password, onSuccess = {}, onError = {
            assertEquals(it?.message, Throwable("No user found").message)
        })
        advanceTimeBy(1000)
    }
}