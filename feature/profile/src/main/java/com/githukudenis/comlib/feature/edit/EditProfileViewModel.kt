
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
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            val result = userRepository.getUserById(userId)
                when(result) {
                    is ResponseResult.Failure ->  {
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
            val user = User(firstname = state.value.firstname, lastname = state.value.lastname)
            state.value.userId?.let {
                val result = userRepository.updateUser(user)
                when(result) {
                    is ResponseResult.Failure -> {
                        update { copy(isUpdating = false, error = result.error.message) }
                    }
                    is ResponseResult.Success -> {
                        update { copy(isUpdating = false) }
                    }
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
        viewModelScope.launch { state.value.userId?.let { userRepository.uploadUserImage(value, it) } }
    }
}
