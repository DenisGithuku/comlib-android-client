package com.githukudenis.comlib.feature.profile.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val firstname: String? = null,
    val lastname: String? = null,
    val profileUrl: String? = null,
    val isUpdateSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val savedStateHandle: SavedStateHandle
) : StatefulViewModel<EditProfileUiState>(EditProfileUiState()) {


    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            val userId: String = checkNotNull(savedStateHandle["userId"])
            update {
                copy(isLoading = true)
            }
            getUserProfileUseCase(userId).also { user ->
                val newState: EditProfileUiState = user?.let {
                    EditProfileUiState(
                        isLoading = false,
                        firstname = user.firstname,
                        lastname = user.lastname,
                        profileUrl = user.image,
                    )
                } ?: EditProfileUiState(
                    isLoading = false, error = "Couldn't fetch profile. Please try again!"
                )
                update {
                    newState
                }
            }
        }
    }

    private fun updateUser() {
        viewModelScope.launch {
            val user = User(
                firstname = state.value.firstname,
                lastname = state.value.lastname,
            )
            updateUserUseCase(
                user
            )
            update {
                copy(isUpdateSuccess = true)
            }
        }
    }
}