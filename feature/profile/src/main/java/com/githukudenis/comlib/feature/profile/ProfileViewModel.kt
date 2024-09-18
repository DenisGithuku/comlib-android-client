
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
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
    private val getUserPrefsUseCase: GetUserPrefsUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    var uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
        private set

    init {
        viewModelScope.launch {
            getUserPrefsUseCase().distinctUntilChanged().collectLatest { prefs ->
                getProfileDetails(prefs.authId ?: return@collectLatest)
            }
        }
    }

    private suspend fun getProfileDetails(userId: String) {
        uiState.update { ProfileUiState(isLoading = true) }
        val user = getUserProfileUseCase(userId)
        user?.toProfile()?.run { uiState.update { ProfileUiState(profile = this, isLoading = false) } }
    }

    fun onSignOut() {
        viewModelScope.launch {
            uiState.update { ProfileUiState(isLoading = true) }
            signOutUseCase().also {
                uiState.update { ProfileUiState(isLoading = false, isSignedOut = true) }
            }
        }
    }

    fun onToggleCache(isVisible: Boolean) {
        uiState.update { it.copy(isClearCache = isVisible) }
    }

    fun onToggleSignOut(isSignOut: Boolean) {
        uiState.update { it.copy(isSignout = isSignOut) }
    }

    fun onChangeUserImage(imageUri: Uri) {
        viewModelScope.launch {
            val userId = getUserPrefsUseCase().first().authId
            checkNotNull(userId).also { userRepository.uploadUserImage(imageUri, userId) }
        }
    }
}
