package com.githukudenis.comlib.feature.home

import com.githukudenis.comlib.core.model.book.BookMilestone

sealed class HomeUiEvent {
    data object Refresh: HomeUiEvent()
    data object NetworkRefresh: HomeUiEvent()
    data class SaveStreak(val bookMilestone: BookMilestone): HomeUiEvent()
}