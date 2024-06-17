
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
package com.githukudenis.comlib.feature.auth.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.domain.usecases.SignUpUseCase
import com.githukudenis.comlib.core.model.UserAuthData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _state: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val state: StateFlow<SignUpUiState>
        get() = _state.asStateFlow()

    private val _showNetworkDialog = MutableStateFlow(false)
    val showNetworkDialog: StateFlow<Boolean>
        get() = _showNetworkDialog.asStateFlow()

    //    private val networkStatus = getNetworkConnectivityUseCase
    //        .networkStatus
    //        .onEach { netStatus ->
    //            _showNetworkDialog.update { netStatus == NetworkStatus.Lost || netStatus ==
    // NetworkStatus.Unavailable }
    //        }
    //        .stateIn(
    //            scope = viewModelScope,
    //            started = SharingStarted.WhileSubscribed(5_000),
    //            initialValue = NetworkStatus.Unavailable
    //        )

    fun onEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.ChangeConfirmPassword -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(confirmPassword = event.confirmPassword)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangeEmail -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(email = event.email)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangeFirstname -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(firstname = event.firstname)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangeLastname -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(lastname = event.lastname)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangePassword -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(password = event.password)
                    prevState.copy(formState = formState)
                }
            }
            SignUpUiEvent.Submit -> {
                signUp()
            }
            is SignUpUiEvent.ToggleConfirmPasswordVisibility -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(confirmPasswordIsVisible = event.isVisible)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.GoogleSignIn -> {
                //                onSignInResult(event.signInResult)
            }
            is SignUpUiEvent.TogglePasswordVisibility -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(passwordIsVisible = event.isVisible)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.DismissUserMessage -> {
                val userMessages = _state.value.userMessages.filterNot { message -> message.id == event.id }
                _state.update { prevState -> prevState.copy(userMessages = userMessages) }
            }
            is SignUpUiEvent.ToggleTerms -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(acceptedTerms = event.accepted)
                    prevState.copy(formState = formState)
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _state.update { prevState -> prevState.copy(isLoading = true) }
            val (firstname, lastname, email, password) = state.value.formState
            val signUpResult =
                signUpUseCase.invoke(
                    UserAuthData(
                        firstname = firstname,
                        lastname = lastname,
                        email = email,
                        password = password
                    )
                )
            when (signUpResult) {
                is ResponseResult.Failure -> {
                    _state.update { prevState ->
                        val userMessages = prevState.userMessages.toMutableList()
                        userMessages.add(UserMessage(message = signUpResult.error.message))
                        prevState.copy(isLoading = false, userMessages = userMessages)
                    }
                }
                is ResponseResult.Success -> {
                    _state.update { prevState -> prevState.copy(isLoading = false, signUpSuccess = true) }
                }
            }
        }
    }

    //    private fun onSignInResult(signInResult: SignInResult) {
    //        viewModelScope.launch {
    //            _state.update { prevState ->
    //                prevState.copy(isLoading = true)
    //            }
    //            if (signInResult.errorMessage != null) {
    //                _state.update { prevState ->
    //                    val userMessages = prevState.userMessages.toMutableList()
    //                    userMessages.add(UserMessage(message = signInResult.errorMessage))
    //                    prevState.copy(
    //                        isLoading = false,
    //                        signUpSuccess = true,
    //                        userMessages = userMessages
    //                    )
    //                }
    //                return@launch
    //            }
    //            val user = signInResult.userData?.run {
    //                User(
    //                    email = email,
    //                    username = username,
    //                    image = profilePictureUrl,
    //                    authId = authId
    //                )
    //            }
    //            userRepository.addNewUser(
    //                user = user ?: return@launch
    //            )
    //            _state.update { prevState ->
    //                val userMessages = prevState.userMessages.toMutableList()
    //                userMessages.add(UserMessage(message = "Signed in successfully"))
    //                prevState.copy(
    //                    isLoading = false,
    //                    signUpSuccess = true,
    //                    userMessages = userMessages
    //                )
    //            }
    //        }
    //    }

    fun onDismissNetworkDialog() {
        _showNetworkDialog.update { false }
    }
}
