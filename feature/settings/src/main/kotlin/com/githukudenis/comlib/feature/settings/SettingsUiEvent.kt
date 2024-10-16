
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

import android.net.Uri
import com.githukudenis.comlib.core.model.ThemeConfig

sealed interface SettingsUiEvent {
    data class ChangeTheme(val themeConfig: ThemeConfig) : SettingsUiEvent

    data class ToggleNotifications(val isToggled: Boolean) : SettingsUiEvent

    data class ToggleAppearance(val isToggled: Boolean) : SettingsUiEvent

    data class ToggleClearCache(val isToggled: Boolean) : SettingsUiEvent

    data class ToggleThemeDialog(val isToggled: Boolean) : SettingsUiEvent

    data class ChangeImage(val imageUri: Uri) : SettingsUiEvent

    data object ResetUpdateError : SettingsUiEvent
}
