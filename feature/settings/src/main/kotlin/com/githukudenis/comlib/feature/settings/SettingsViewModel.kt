
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
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

data class Profile(
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val imageUrl: String? = null
)

sealed interface ProfileItemState {
    data object Loading : ProfileItemState

    data class Success(val profile: Profile) : ProfileItemState

    data class Error(val message: String) : ProfileItemState
}

fun User.toProfile(): Profile {
    return Profile(firstname = firstname, lastname = lastname, email = email, imageUrl = image)
}

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val state: StateFlow<SettingsUiState> =
        userPrefsRepository.userPrefs
            .mapLatest { prefs ->
                prefs.userId?.let { id ->
                    val profile =
                        when (val result = userRepository.getUserById(id)) {
                            is ResponseResult.Failure -> ProfileItemState.Error(result.error.message)
                            is ResponseResult.Success ->
                                ProfileItemState.Success(result.data.data.user.toProfile())
                        }
                    SettingsUiState(profileItemState = profile)
                } ?: SettingsUiState()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState()
            )
}
