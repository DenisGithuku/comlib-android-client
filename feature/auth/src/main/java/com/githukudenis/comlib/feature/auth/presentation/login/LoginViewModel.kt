
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
package com.githukudenis.comlib.feature.auth.presentation.login

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.data.repository.AuthRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.user.UserLoginDTO
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements.CapitalLetter
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements.Length
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements.Number
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements.SpecialCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
    private val userPrefsRepository: UserPrefsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState>
        get() = _state.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.ChangeEmail -> {
                changeEmail(event.email)
            }
            is LoginUiEvent.ChangePassword -> {
                changePassword(event.password)
            }
            is LoginUiEvent.GoogleSignIn -> {
                //                onSignInResult(event.signInResult)
            }
            is LoginUiEvent.DismissUserMessage -> {
                dismissUserMessage(event.id)
            }
            LoginUiEvent.SubmitData -> {
                login()
            }
            is LoginUiEvent.TogglePassword -> {
                togglePassword(event.isVisible)
            }
            is LoginUiEvent.ToggleRememberMe -> {
                toggleRememberMe(event.remember)
            }
            LoginUiEvent.ResetState -> {
                resetState()
            }
        }
    }

    private fun toggleRememberMe(value: Boolean) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(rememberMe = value)
            prevState.copy(formState = formState)
        }
    }

    private fun changeEmail(value: String) {
        _state.update { prevState ->
            val formState =
                prevState.formState.copy(
                    email = value,
                    isEmailValid = PatternsCompat.EMAIL_ADDRESS.matcher(value).matches()
                )
            prevState.copy(formState = formState)
        }
    }

    private fun changePassword(password: String) {
        _state.update { prevState ->
            val specialSymbolsRegex = "[!@#\$%^&*()_+\\-=\\[\\]{}|;:'\",.<>?]\n"
            val requirements = mutableListOf<PasswordRequirements>()
            if (password.length > 7) {
                requirements.add(Length)
            }
            if (password.any { it.isDigit() }) {
                requirements.add(Number)
            }
            if (password.any { it.isUpperCase() }) {
                requirements.add(CapitalLetter)
            }
            if (password.any { it in specialSymbolsRegex }) {
                requirements.add(SpecialCharacter)
            }
            val formState = prevState.formState.copy(password = password, requirements = requirements)
            prevState.copy(formState = formState)
        }
    }

    private fun dismissUserMessage(id: Int) {
        val messages = _state.value.userMessages.filterNot { it.id == id }
        _state.update { prevState -> prevState.copy(userMessages = messages) }
    }

    private fun togglePassword(isVisible: Boolean) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(passwordIsVisible = isVisible)
            prevState.copy(formState = formState)
        }
    }

    private fun login() {
        viewModelScope.launch {
            val (email, password) = _state.value.formState
            if (email.trim().isEmpty() || password.trim().isEmpty()) {
                _state.update { prevState ->
                    val userMessages = prevState.userMessages.toMutableList()
                    userMessages.add(
                        UserMessage(message = "Please check your details", messageType = MessageType.ERROR)
                    )
                    prevState.copy(userMessages = userMessages)
                }
                return@launch
            }

            _state.update { prevState -> prevState.copy(isLoading = true) }

            authRepository.login(
                userLogInDTO = UserLoginDTO(email = email.trim(), password = password.trim()),
                onSuccess = { response ->
                    // Store token and id
                    userPrefsRepository.setToken(response.token)
                    userPrefsRepository.setUserId(response.id)

                    // Get user details
                    when (val result = userRepository.getUserById(response.id)) {
                        is ResponseResult.Failure -> {
                            val userMessages = _state.value.userMessages.toMutableList()
                            userMessages.add(UserMessage(message = result.error.message))
                            _state.update { prevState ->
                                prevState.copy(isLoading = false, loginSuccess = false, userMessages = userMessages)
                            }
                        }
                        is ResponseResult.Success -> {
                            userPrefsRepository.setSetupStatus(result.data.data.user.image != null)

                            // If coming back to the app after logout, refresh user image
                            // Await profile path
                            val userImageUrl = result.data.data.user.image
                            userImageUrl?.run {
                                val profilePathDeferred = async {
                                    userPrefsRepository.setProfilePicturePath(userImageUrl)
                                }

                                // Get reference to user data
                                val userProfileData = userPrefsRepository.userPrefs.first().userProfileData

                                userPrefsRepository.setUserProfileData(
                                    userProfileData.copy(
                                        email = result.data.data.user.email,
                                        username = result.data.data.user.username,
                                        profilePicturePath = profilePathDeferred.await(),
                                        firstname = result.data.data.user.firstname,
                                        lastname = result.data.data.user.lastname
                                    )
                                )
                            }
                        }
                    }
                    _state.update { prevState -> prevState.copy(isLoading = false, loginSuccess = true) }
                },
                onError = { error ->
                    val userMessages = _state.value.userMessages.toMutableList()
                    userMessages.add(UserMessage(message = error))
                    _state.update { prevState ->
                        prevState.copy(isLoading = false, loginSuccess = false, userMessages = userMessages)
                    }
                }
            )
        }
    }

    private fun resetState() {
        _state.update { LoginUiState() }
    }

    private fun storeUserProfileData(id: String, onFail: (String) -> Unit, onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = userRepository.getUserById(id)) {
                is ResponseResult.Failure -> {
                    onFail(result.error.message)
                }
                is ResponseResult.Success -> {
                    val userProfileData = userPrefsRepository.userPrefs.first().userProfileData

                    onSuccess()
                }
            }
        }
    }
}
