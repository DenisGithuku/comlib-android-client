package com.githukudenis.comlib.feature.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
        private set

    init {
         savedStateHandle.get<String>("userId")?.let {
             getProfileDetails(it)
         }
    }

    private fun getProfileDetails(userId: String) {
        viewModelScope.launch {
            uiState.update {
                ProfileUiState(isLoading = true)
            }
            val user = comlibUseCases.getUserProfileUseCase(userId)
            user?.toProfile()?.run {
                uiState.update {
                    ProfileUiState(
                        profile = this,
                        isLoading = false
                    )
                }
            }
        }
    }
}