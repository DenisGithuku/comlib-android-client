package com.githukudenis.comlib

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {
    private val _state: MutableStateFlow<MainActivityUiState> = MutableStateFlow(MainActivityUiState())
    val state: StateFlow<MainActivityUiState> get() = _state.asStateFlow()

    init {
        if (firebaseAuth.currentUser != null) {
            _state.value = _state.value.copy(isLoggedIn = true)
        }
    }
}

data class MainActivityUiState(
    val isLoggedIn: Boolean = false,
)