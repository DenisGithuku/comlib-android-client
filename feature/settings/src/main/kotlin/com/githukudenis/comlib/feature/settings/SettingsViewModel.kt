
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

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.ThemeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
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
            is SettingsUiEvent.ChangeImage -> onChangePhoto(event.imageUri)
            is SettingsUiEvent.ResetUpdateError -> resetUpdateError()
        }
    }

    private fun resetUpdateError() {
        _state.update { it.copy(updateError = null) }
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

    private fun onChangePhoto(value: Uri) {
        viewModelScope.launch {
            val userId = userPrefsRepository.userPrefs.first().userId
            val response = userRepository.getUserById(userId ?: return@launch)
            val user =
                when (response) {
                    is ResponseResult.Failure -> {
                        _state.update { it.copy(updateError = response.error.message) }
                        return@launch
                    }
                    is ResponseResult.Success -> {
                        response.data.data.user
                    }
                }

            _state.update { it.copy(isUpdating = true) }

            // Upload image to store first
            val uploadImageRes =
                value.let {
                    userRepository.uploadUserImage(imageUri = value, userId = userId, isNewUser = false)
                }

            when (uploadImageRes) {
                is ResponseResult.Failure -> {
                    _state.update { it.copy(updateError = uploadImageRes.error.message) }
                }
                is ResponseResult.Success -> {
                    val updatedUser = user.copy(image = uploadImageRes.data)

                    // Update remote user with new image
                    when (val updateUserResult = userRepository.updateUser(updatedUser)) {
                        is ResponseResult.Failure -> {
                            _state.update { it.copy(updateError = updateUserResult.error.message) }
                            _state.update { it.copy(isUpdating = false) }
                        }
                        is ResponseResult.Success -> {
                            // Update local user with new image
                            val userProfileData = userPrefsRepository.userPrefs.first().userProfileData

                            // Wait for user image to be updated
                            val imagePathDeferred = async {
                                userPrefsRepository.setProfilePicturePath(uploadImageRes.data)
                            }

                            // Update local user with new image
                            val imagePath = imagePathDeferred.await()
                            userPrefsRepository.setUserProfileData(
                                userProfileData.copy(profilePicturePath = imagePath)
                            )
                            _state.update {
                                it.copy(
                                    isUpdating = false,
                                    isUpdateComplete = true,
                                    userProfileData = it.userProfileData.copy(profilePicturePath = imagePath)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
