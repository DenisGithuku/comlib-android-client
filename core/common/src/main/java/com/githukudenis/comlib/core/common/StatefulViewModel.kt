package com.githukudenis.comlib.core.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * project : ComLib
 * date    : Friday 23/02/2024
 * time    : 12:08 pm
 * user    : mambo
 * email   : mambobryan@gmail.com
 */

abstract class StatefulViewModel<T>(initial: T) : ViewModel() {

    private val _state = MutableStateFlow(initial)
    val state get() = _state.asStateFlow()

    fun update(block: T.() -> T) {
        _state.update(block)
    }

}