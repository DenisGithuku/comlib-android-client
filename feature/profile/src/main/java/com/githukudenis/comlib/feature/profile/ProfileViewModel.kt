
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
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.AuthRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.ThemeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    private val _userPrefs = userPrefsRepository.userPrefs

    val uiState: StateFlow<ProfileUiState> =
        combine(_uiState, _userPrefs) { uiState, prefs ->
                prefs.userId?.let { id ->
                    val profile =
                        when (val result = userRepository.getUserById(id)) {
                            is ResponseResult.Failure -> ProfileItemState.Error(result.error.message)
                            is ResponseResult.Success ->
                                ProfileItemState.Success(result.data.data.user.toProfile())
                        }
                    uiState.copy(profileItemState = profile, selectedTheme = prefs.themeConfig)
                } ?: ProfileUiState()
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

    private fun toggleThemeDialog(isVisible: Boolean) {
        _uiState.update { it.copy(isThemeDialogOpen = isVisible) }
    }

    private fun onSignOut() {
        viewModelScope.launch {
            _uiState.update { ProfileUiState(isLoading = true) }
            authRepository.signOut()
            _uiState.update { it.copy(isLoading = false, isSignedOut = true) }
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
            val userId = _userPrefs.mapLatest { it.userId }.first()
            checkNotNull(userId).run { uploadUserImage(imageUri = imageUri, userId = this) }
        }
    }

    private fun toggleTheme(theme: ThemeConfig) {
        viewModelScope.launch { userPrefsRepository.setThemeConfig(theme) }
    }

    private suspend fun uploadUserImage(imageUri: Uri, userId: String) {
        when (val result = userRepository.getUserById(userId)) {
            is ResponseResult.Failure -> {
                _uiState.update { it.copy(error = result.error.message) }
            }
            is ResponseResult.Success -> {
                // Upload image to store first
                val uploadImageRes =
                    userId.let {
                        userRepository.uploadUserImage(imageUri = imageUri, userId = userId, isNewUser = false)
                    }

                when (uploadImageRes) {
                    is ResponseResult.Failure -> {
                        _uiState.update { it.copy(error = uploadImageRes.error.message, isLoading = false) }
                    }
                    is ResponseResult.Success -> {
                        val updatedUser = result.data.data.user.copy(image = uploadImageRes.data)

                        // Update user with new image
                        when (val updateUserResult = userRepository.updateUser(updatedUser)) {
                            is ResponseResult.Failure -> {
                                _uiState.update {
                                    it.copy(error = updateUserResult.error.message, isLoading = false)
                                }
                            }
                            is ResponseResult.Success -> {
                                _uiState.update { it.copy(isLoading = false) }
                            }
                        }
                    }
                }
            }
        }
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
