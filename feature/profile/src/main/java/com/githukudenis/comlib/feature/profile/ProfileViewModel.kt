package com.githukudenis.comlib.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases
) : ViewModel() {

    var uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
        private set

    init {
        viewModelScope.launch {
            comlibUseCases.getUserPrefsUseCase().distinctUntilChanged().collectLatest { prefs ->
                    getProfileDetails(prefs.userId ?: return@collectLatest)
                }
        }
    }

    private suspend fun getProfileDetails(userId: String) {
        uiState.update {
            ProfileUiState(isLoading = true)
        }
        val user = comlibUseCases.getUserProfileUseCase(userId)
        user?.toProfile()?.run {
            uiState.update {
                ProfileUiState(
                    profile = this, isLoading = false
                )
            }
        }
    }

    fun onSignOut() {
        viewModelScope.launch {
            uiState.update { ProfileUiState(isLoading = true) }
            comlibUseCases.signOutUseCase().also {
                uiState.update { ProfileUiState(isLoading = false, isSignedOut = true) }
            }
        }
    }

    fun onToggleCache(isVisible: Boolean) {
        uiState.update {
            it.copy(isClearCache = isVisible)
        }
    }
    fun onToggleSignOut(isSignOut: Boolean) {
        uiState.update {
            it.copy(isSignout = isSignOut)
        }
    }
}