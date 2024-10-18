
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
package com.githukudenis.comlib.core.model

data class UserPrefs(
    val themeConfig: ThemeConfig = ThemeConfig.SYSTEM,
    val token: String? = null,
    val userId: String? = null,
    val readBooks: Set<String> = emptySet(),
    val bookmarkedBooks: Set<String> = emptySet(),
    val reservedBooks: Set<String> = emptySet(),
    val isSetup: Boolean = false,
    val preferredGenres: Set<String> = emptySet(),
    val isNotificationsEnabled: Boolean = false,
    val userProfileData: UserProfileData = UserProfileData()
)

data class UserProfileData(
    val username: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val profilePicturePath: String? = null
)

enum class ThemeConfig {
    SYSTEM,
    LIGHT,
    DARK
}

class ThemeConfigConverter {
    companion object {
        fun fromThemeConfig(value: ThemeConfig): String {
            return value.name
        }

        fun toThemeConfig(value: String): ThemeConfig {
            return ThemeConfig.valueOf(value)
        }
    }
}
