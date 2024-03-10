package com.githukudenis.comlib.feature.auth.presentation.reset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state: MutableStateFlow<ResetUiState> = MutableStateFlow(ResetUiState())
        private set

    fun onEmailChange(newValue: String) {
        state.update { prevState -> prevState.copy(email = newValue) }

    }

    fun onReset() {
        viewModelScope.launch {
            val email = state.value.email
            state.update { prevState -> prevState.copy(isLoading = true) }
            authRepository.resetPassword(email, onSuccess = {
                state.update { prevState ->
                    prevState.copy(
                        isLoading = false, isSuccess = true
                    )
                }
            }, onError = {
                state.update { prevState ->
                    prevState.copy(
                        error = UserMessage(
                            message = it?.message, messageType = MessageType.ERROR
                        )
                    )
                }
            })

        }
    }

    fun onErrorShown() {
        state.update {
            it.copy(
                error = null
            )
        }
    }
}