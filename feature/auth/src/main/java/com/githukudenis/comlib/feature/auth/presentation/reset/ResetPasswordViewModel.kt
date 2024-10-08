
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
package com.githukudenis.comlib.feature.auth.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _state: MutableStateFlow<ResetUiState> = MutableStateFlow(ResetUiState())
    val state: StateFlow<ResetUiState>
        get() = _state.asStateFlow()

    fun onEmailChange(newValue: String) {
        _state.update { prevState -> prevState.copy(email = newValue) }
    }

    fun onReset() {
        viewModelScope.launch {
            val email = state.value.email
            _state.update { prevState -> prevState.copy(isLoading = true) }
            authRepository.resetPassword(
                email,
                onSuccess = {
                    _state.update { prevState -> prevState.copy(isLoading = false, isSuccess = true) }
                },
                onError = {
                    _state.update { prevState ->
                        prevState.copy(error = UserMessage(message = it, messageType = MessageType.ERROR))
                    }
                }
            )
        }
    }

    fun onErrorShown() {
        _state.update { it.copy(error = null) }
    }
}
