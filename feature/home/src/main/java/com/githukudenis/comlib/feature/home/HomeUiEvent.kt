package com.githukudenis.comlib.feature.home

sealed class HomeUiEvent {
    data object Refresh: HomeUiEvent()
}