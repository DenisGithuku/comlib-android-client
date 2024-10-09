
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
package com.githukudenis.comlib.feature.edit

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val userId: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val username: String? = null,
    val profileUrl: String? = null,
    val isUpdating: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EditProfileViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val userRepository: UserRepository
) : StatefulViewModel<EditProfileUiState>(EditProfileUiState()) {

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            val userId: String = checkNotNull(userPrefsRepository.userPrefs.first().userId)
            update { copy(isLoading = true) }
            when (val result = userRepository.getUserById(userId)) {
                is ResponseResult.Failure -> {
                    update { copy(isLoading = false, error = result.error.message) }
                }
                is ResponseResult.Success -> {
                    val newState: EditProfileUiState =
                        EditProfileUiState(
                            isLoading = false,
                            userId = result.data.data.user.id,
                            firstname = result.data.data.user.firstname,
                            username = result.data.data.user.username,
                            lastname = result.data.data.user.lastname,
                            profileUrl = result.data.data.user.image
                        )
                    update { newState }
                }
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            update { copy(isUpdating = true) }
            val user =
                User(
                    firstname = state.value.firstname,
                    lastname = state.value.lastname,
                    id = state.value.userId
                )

            when (val result = userRepository.updateUser(user)) {
                is ResponseResult.Failure -> {
                    update { copy(isUpdating = false, error = result.error.message) }
                }
                is ResponseResult.Success -> {
                    update { copy(isUpdating = false) }
                }
            }
        }
    }

    fun onChangeFirstname(value: String) {
        update { copy(firstname = value) }
    }

    fun onChangeLastname(value: String) {
        update { copy(lastname = value) }
    }

    fun onChangeUsername(value: String) {
        update { copy(username = value) }
    }

    fun onChangePhoto(value: Uri) {
        viewModelScope.launch {
            when (val result = userRepository.getUserById(state.value.userId ?: return@launch)) {
                is ResponseResult.Failure -> {
                    update { copy(error = result.error.message) }
                }
                is ResponseResult.Success -> {
                    // Upload image to store first
                    val uploadImageRes =
                        value.let {
                            userRepository.uploadUserImage(
                                imageUri = value,
                                userId = state.value.userId ?: return@launch,
                                isNewUser = false
                            )
                        }

                    when (uploadImageRes) {
                        is ResponseResult.Failure -> {
                            update { copy(error = uploadImageRes.error.message, isLoading = false) }
                        }
                        is ResponseResult.Success -> {
                            val updatedUser = result.data.data.user.copy(image = uploadImageRes.data)

                            // Update user with new image
                            when (val updateUserResult = userRepository.updateUser(updatedUser)) {
                                is ResponseResult.Failure -> {
                                    update { copy(error = updateUserResult.error.message, isLoading = false) }
                                }
                                is ResponseResult.Success -> {
                                    update { copy(isLoading = false) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
