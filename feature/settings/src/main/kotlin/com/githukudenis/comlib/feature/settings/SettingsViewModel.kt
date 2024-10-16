
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
package com.githukudenis.comlib.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.ThemeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SettingsUiState> = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            userPrefsRepository.userPrefs.collectLatest { prefs ->
                _state.update { state ->
                    state.copy(
                        selectedTheme = prefs.themeConfig,
                        userProfileData = prefs.userProfileData,
                        isNotificationsToggled = prefs.isNotificationsEnabled
                    )
                }
            }
        }
    }

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.ChangeTheme -> changeTheme(event.themeConfig)
            is SettingsUiEvent.ToggleNotifications -> toggleNotifications(event.isToggled)
            is SettingsUiEvent.ToggleAppearance -> toggleAppearance(event.isToggled)
            is SettingsUiEvent.ToggleClearCache -> toggleClearCache(event.isToggled)
            is SettingsUiEvent.ToggleThemeDialog -> toggleThemeDialog(event.isToggled)
        }
    }

    private fun changeTheme(themeConfig: ThemeConfig) {
        viewModelScope.launch { userPrefsRepository.setThemeConfig(themeConfig) }
    }

    private fun toggleNotifications(isToggled: Boolean) {
        viewModelScope.launch { userPrefsRepository.toggleNotifications(isToggled) }
    }

    private fun toggleAppearance(isToggled: Boolean) {
        _state.update { state -> state.copy(isAppearanceSheetVisible = isToggled) }
    }

    private fun toggleClearCache(isToggled: Boolean) {
        _state.update { state -> state.copy(isCacheDialogVisible = isToggled) }
    }

    private fun toggleThemeDialog(isToggled: Boolean) {
        _state.update { state -> state.copy(isThemeDialogVisible = isToggled) }
    }
}
