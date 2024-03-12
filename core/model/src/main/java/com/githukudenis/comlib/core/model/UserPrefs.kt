package com.githukudenis.comlib.core.model

data class UserPrefs(
    val themeConfig: ThemeConfig =  ThemeConfig.SYSTEM,
    val userId: String? = null,
    val readBooks: Set<String> = emptySet(),
    val bookmarkedBooks: Set<String> = emptySet(),
    val isSetup: Boolean = false,
    val preferredGenres: Set<String> = emptySet()
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