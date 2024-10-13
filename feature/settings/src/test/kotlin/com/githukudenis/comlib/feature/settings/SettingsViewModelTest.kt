package com.githukudenis.comlib.feature.settings

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserRepository
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@MediumTest
class SettingsViewModelTest {

    @get:Rule
    val mainCoroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    private lateinit var userPrefsRepository: UserPrefsRepository
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        userPrefsRepository = FakeUserPrefsRepository()
        userRepository = FakeUserRepository()
        viewModel = SettingsViewModel(
            userPrefsRepository = userPrefsRepository,
            userRepository = userRepository
        )
    }

    @Test
    fun testInitialization() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }
        advanceUntilIdle()

        assertEquals(
            viewModel.state.value.uiComponentsState, UiComponentsState()
        )
        assertEquals(viewModel.state.value.selectedTheme, ThemeConfig.SYSTEM)

    }

    @Test
    fun testChangeTheme() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.onEvent(SettingsUiEvent.ChangeTheme(ThemeConfig.DARK))
        advanceUntilIdle()
        assertEquals(viewModel.state.value.selectedTheme, ThemeConfig.DARK)
    }

    @Test
    fun testToggleNotifications() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.onEvent(SettingsUiEvent.ToggleNotifications(true))
        advanceUntilIdle()
        assertTrue(viewModel.state.value.isNotificationsToggled)
    }

    @Test
    fun testToggleAppearance() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.onEvent(SettingsUiEvent.ToggleAppearance(true))
        assertTrue(viewModel.state.value.uiComponentsState.isAppearanceSheetVisible)
    }

    @Test
    fun testToggleClearCache() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.onEvent(SettingsUiEvent.ToggleClearCache(true))
        assertTrue(viewModel.state.value.uiComponentsState.isCacheDialogVisible)
    }

    @Test
    fun testToggleChangeTheme() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }

        viewModel.onEvent(SettingsUiEvent.ToggleThemeDialog(true))
        assertTrue(viewModel.state.value.uiComponentsState.isThemeDialogVisible)
    }

}