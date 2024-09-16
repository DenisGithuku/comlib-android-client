
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
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val userId: String? = null,
    val authId: String? = null,
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
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserPrefsUseCase: GetUserPrefsUseCase,
    private val userRepository: UserRepository
) : StatefulViewModel<EditProfileUiState>(EditProfileUiState()) {

    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            val userId: String = checkNotNull(getUserPrefsUseCase().first().authId)
            update { copy(isLoading = true) }
            getUserProfileUseCase(userId).also { user ->
                val newState: EditProfileUiState =
                    user?.let {
                        EditProfileUiState(
                            isLoading = false,
                            userId = user.id,
                            firstname = user.firstname,
                            username = user.username,
                            lastname = user.lastname,
                            profileUrl = user.image,
                            authId = user.authId
                        )
                    }
                        ?: EditProfileUiState(
                            isLoading = false,
                            error = "Couldn't fetch profile. Please try again!"
                        )
                update { newState }
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            update { copy(isUpdating = true) }
            val user = User(firstname = state.value.firstname, lastname = state.value.lastname)
            state.value.userId?.let { updateUserUseCase(it, user) }
            update { copy(isUpdating = false) }
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
            state.value.userId?.let { userRepository.uploadUserImage(value, it) }
        }
    }
}
