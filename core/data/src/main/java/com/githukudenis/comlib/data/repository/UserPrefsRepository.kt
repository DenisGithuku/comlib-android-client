package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserPrefs
import kotlinx.coroutines.flow.Flow

interface UserPrefsRepository {

    val userPrefs: Flow<UserPrefs>

    suspend fun setUserId(userId: String)

    suspend fun setThemeConfig(themeConfig: ThemeConfig)
    suspend fun setBookMarks(bookMarks: Set<String>)

}