
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
package com.githukudenis.comlib

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.model.ThemeConfig
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val getUserPrefsUseCase: GetUserPrefsUseCase
) : ViewModel() {

    val state: StateFlow<MainActivityUiState> =
        getUserPrefsUseCase()
            .mapLatest { userPrefs ->
                MainActivityUiState(
                    isLoading = false,
                    isLoggedIn = firebaseAuth.currentUser != null,
                    isSetup = userPrefs.isSetup,
                    themeConfig = userPrefs.themeConfig
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MainActivityUiState())
}

data class MainActivityUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isSetup: Boolean = false,
    val themeConfig: ThemeConfig = ThemeConfig.SYSTEM
)
