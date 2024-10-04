
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
package com.githukudenis.comlib.feature.auth.presentation.complete_profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CompleteProfileUiState(
    val isLoading: Boolean = false,
    val selectedImageUri: Uri? = null,
    val userName: String = "",
    val isSuccess: Boolean = false,
    val userMessages: List<UserMessage> = emptyList()
) {
    val isUiValid: Boolean
        get() = selectedImageUri != null && userName.length >= 4
}

@HiltViewModel
class CompleteProfileViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<CompleteProfileUiState> =
        MutableStateFlow(CompleteProfileUiState())
    val state: StateFlow<CompleteProfileUiState> = _state.asStateFlow()

    fun onSelectImage(uri: Uri) {
        _state.update { prevState -> prevState.copy(selectedImageUri = uri) }
    }

    fun onSelectUserName(userName: String) {
        _state.update { prevState -> prevState.copy(userName = userName) }
    }

    fun onDismissUserMessage() {
        _state.update { prevState -> prevState.copy(userMessages = prevState.userMessages.drop(1)) }
    }

    fun onSubmit() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            userPrefsRepository.userPrefs
                .mapLatest { it.userId }
                .first()
                ?.let {
                    when (val result = userRepository.getUserById(it)) {
                        is ResponseResult.Failure -> {
                            _state.update { prevState ->
                                prevState.copy(
                                    userMessages =
                                        prevState.userMessages + UserMessage(message = result.error.message),
                                    isLoading = false
                                )
                            }
                        }
                        is ResponseResult.Success -> {
                            val user = result.data.data.user
                            val selectedImageUri = _state.value.selectedImageUri
                            val userName = _state.value.userName

                            // Upload image to store first
                            val uploadImageRes =
                                selectedImageUri?.let {
                                    userRepository.uploadUserImage(
                                        imageUri = selectedImageUri,
                                        userId = user.id ?: return@launch,
                                        isNewUser = true
                                    )
                                } ?: return@launch

                            when (uploadImageRes) {
                                is ResponseResult.Failure -> {
                                    _state.update { prevState ->
                                        prevState.copy(
                                            userMessages =
                                                prevState.userMessages +
                                                    UserMessage(message = uploadImageRes.error.message),
                                            isLoading = false
                                        )
                                    }
                                }
                                is ResponseResult.Success -> {
                                    val updatedUser = user.copy(username = userName, image = uploadImageRes.data)

                                    // Update user with new image
                                    when (val updateUserResult = userRepository.updateUser(updatedUser)) {
                                        is ResponseResult.Failure -> {
                                            _state.update { prevState ->
                                                prevState.copy(
                                                    userMessages =
                                                        prevState.userMessages +
                                                            UserMessage(message = updateUserResult.error.message),
                                                    isLoading = false
                                                )
                                            }
                                        }
                                        is ResponseResult.Success -> {
                                            _state.update { prevState ->
                                                prevState.copy(
                                                    isSuccess = true,
                                                    isLoading = false,
                                                    userMessages =
                                                        prevState.userMessages +
                                                            UserMessage(message = "Profile updated successfully")
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } ?: return@launch
        }
    }
}
