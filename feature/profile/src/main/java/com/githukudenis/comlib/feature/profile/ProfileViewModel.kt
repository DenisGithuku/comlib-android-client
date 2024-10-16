
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
import com.githukudenis.comlib.core.model.user.User
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

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isSignOutActivated: Boolean = false,
    val isSignOutComplete: Boolean = false,
    val user: User = User(),
    val isUpdating: Boolean = false,
    val isUpdateComplete: Boolean = false,
    val fetchError: String? = null,
    val updateError: String? = null
)

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state.asStateFlow()

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            userPrefsRepository.userPrefs.collectLatest { prefs ->
                _state.update { state ->
                    state.copy(
                        user =
                            User(
                                id = prefs.userId,
                                firstname = prefs.userProfileData.firstname,
                                lastname = prefs.userProfileData.lastname,
                                username = prefs.userProfileData.username,
                                email = prefs.userProfileData.email,
                                image = prefs.userProfileData.profilePicturePath
                            )
                    )
                }
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            _state.update { it.copy(isUpdating = true) }
            when (val result = userRepository.updateUser(_state.value.user)) {
                is ResponseResult.Failure -> {
                    _state.update { it.copy(isUpdating = false, updateError = result.error.message) }
                }
                is ResponseResult.Success -> {
                    val userProfileData = userPrefsRepository.userPrefs.first().userProfileData
                    userPrefsRepository.setUserProfileData(
                        userProfileData.copy(
                            firstname = _state.value.user.firstname,
                            lastname = _state.value.user.lastname,
                            username = _state.value.user.username
                        )
                    )
                    _state.update { it.copy(isUpdating = false, isUpdateComplete = true) }
                }
            }
        }
        getUserDetails()
    }

    fun onResetUpdateStatus() {
        _state.update { it.copy(isUpdateComplete = false) }
    }

    fun onChangeFirstname(value: String) {
        _state.update { it.copy(user = it.user.copy(firstname = value.trim())) }
    }

    fun onChangeLastname(value: String) {
        _state.update { it.copy(user = it.user.copy(lastname = value.trim())) }
    }

    fun onChangeUsername(value: String) {
        _state.update { it.copy(user = it.user.copy(username = value.trim())) }
    }

    fun onChangePhoto(value: Uri) {
        viewModelScope.launch {
            _state.update { it.copy(isUpdating = true) }

            // Upload image to store first
            val uploadImageRes =
                value.let {
                    userRepository.uploadUserImage(
                        imageUri = value,
                        userId = _state.value.user.id ?: return@launch,
                        isNewUser = false
                    )
                }

            when (uploadImageRes) {
                is ResponseResult.Failure -> {
                    _state.update { it.copy(updateError = uploadImageRes.error.message) }
                }
                is ResponseResult.Success -> {
                    val updatedUser = _state.value.user.copy(image = uploadImageRes.data)

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

                            userPrefsRepository.setUserProfileData(
                                userProfileData.copy(profilePicturePath = imagePathDeferred.await())
                            )
                            _state.update { it.copy(isUpdating = false, isUpdateComplete = true) }
                        }
                    }
                }
            }
        }
        getUserDetails()
    }

    fun onToggleSignOut(value: Boolean) {
        _state.update { it.copy(isSignOutActivated = value) }
    }

    fun onSignOut() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            authRepository.signOut()
            _state.update { it.copy(isLoading = false, isSignOutComplete = true) }
        }
    }
}
