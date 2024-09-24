
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

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.SignOutUseCase
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val getUserPrefsUseCase: GetUserPrefsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val userRepository: UserRepository,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    private val _userPrefs = getUserPrefsUseCase()

    val uiState: StateFlow<ProfileUiState> =
        combine(_uiState, _userPrefs) { uiState, prefs ->
                val profile = prefs.authId?.let { id -> getProfileDetails(id) }
                uiState.copy(profile = profile, selectedTheme = prefs.themeConfig)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ProfileUiState()
            )

    fun onEvent(profileUiEvent: ProfileUiEvent) {
        when (profileUiEvent) {
            is ProfileUiEvent.ChangeTheme -> {
                toggleTheme(profileUiEvent.theme)
            }
            is ProfileUiEvent.ChangeUserImage -> {
                onChangeUserImage(profileUiEvent.imageUri)
            }
            ProfileUiEvent.SignOut -> {
                onSignOut()
            }
            is ProfileUiEvent.ToggleCache -> {
                onToggleCache(profileUiEvent.isVisible)
            }
            is ProfileUiEvent.ToggleSignOut -> {
                onToggleSignOut(profileUiEvent.isSignOut)
            }
            is ProfileUiEvent.ToggleThemeDialog -> {
                toggleThemeDialog(profileUiEvent.isVisible)
            }
        }
    }

    private suspend fun getProfileDetails(authId: String): Profile {
        val user = getUserProfileUseCase(authId)
        return user?.toProfile() ?: Profile()
    }

    private fun toggleThemeDialog(isVisible: Boolean) {
        _uiState.update { it.copy(isThemeDialogOpen = isVisible) }
    }

    private fun onSignOut() {
        viewModelScope.launch {
            _uiState.update { ProfileUiState(isLoading = true) }
            val result = signOutUseCase()
            _uiState.update { it.copy(isLoading = false, isSignedOut = result) }
        }
    }

    private fun onToggleCache(isVisible: Boolean) {
        _uiState.update { it.copy(isClearCache = isVisible) }
    }

    private fun onToggleSignOut(isSignOut: Boolean) {
        _uiState.update { it.copy(isSignout = isSignOut) }
    }

    private fun onChangeUserImage(imageUri: Uri) {
        viewModelScope.launch {
            val userId = getUserPrefsUseCase().first().authId
            checkNotNull(userId).also { userRepository.uploadUserImage(imageUri, userId) }
        }
    }

    private fun toggleTheme(theme: ThemeConfig) {
        viewModelScope.launch { userPrefsRepository.setThemeConfig(theme) }
    }
}

sealed interface ProfileUiEvent {
    data class ToggleCache(val isVisible: Boolean) : ProfileUiEvent

    data class ToggleSignOut(val isSignOut: Boolean) : ProfileUiEvent

    data class ChangeTheme(val theme: ThemeConfig) : ProfileUiEvent

    data class ChangeUserImage(val imageUri: Uri) : ProfileUiEvent

    data class ToggleThemeDialog(val isVisible: Boolean) : ProfileUiEvent

    data object SignOut : ProfileUiEvent
}
