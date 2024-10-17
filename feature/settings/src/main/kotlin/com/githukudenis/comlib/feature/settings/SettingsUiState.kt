
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
package com.githukudenis.comlib.feature.settings

import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserProfileData

data class SettingsUiState(
    val availableThemes: List<ThemeConfig> =
        listOf(ThemeConfig.SYSTEM, ThemeConfig.LIGHT, ThemeConfig.DARK),
    val userProfileData: UserProfileData = UserProfileData(),
    val selectedTheme: ThemeConfig = ThemeConfig.SYSTEM,
    val isNotificationsToggled: Boolean = false,
    val isCacheDialogVisible: Boolean = false,
    val isAppearanceSheetVisible: Boolean = false,
    val isThemeDialogVisible: Boolean = false,
    val isUpdating: Boolean = false,
    val isUpdateComplete: Boolean = false,
    val updateError: String? = null
)
