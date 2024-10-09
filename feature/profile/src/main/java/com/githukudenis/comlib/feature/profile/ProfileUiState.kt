
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

import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.user.User

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profileItemState: ProfileItemState = ProfileItemState.Loading,
    val error: String? = null,
    val isSignedOut: Boolean = false,
    val isClearCache: Boolean = false,
    val isSignout: Boolean = false,
    val isThemeDialogOpen: Boolean = false,
    val availableThemes: List<ThemeConfig> =
        listOf(ThemeConfig.SYSTEM, ThemeConfig.LIGHT, ThemeConfig.DARK),
    val selectedTheme: ThemeConfig = ThemeConfig.SYSTEM
)

data class Profile(
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val imageUrl: String? = null
)

sealed interface ProfileItemState {
    data object Loading : ProfileItemState

    data class Success(val profile: Profile) : ProfileItemState

    data class Error(val message: String) : ProfileItemState
}

fun User.toProfile(): Profile {
    return Profile(firstname = firstname, lastname = lastname, email = email, imageUrl = image)
}
