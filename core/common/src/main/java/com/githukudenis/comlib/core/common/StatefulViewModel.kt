
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
package com.githukudenis.comlib.core.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * project : ComLib date : Friday 23/02/2024 time : 12:08 pm user : mambo email :
 * mambobryan@gmail.com
 */
abstract class StatefulViewModel<T>(initial: T) : ViewModel() {

    private val _state = MutableStateFlow(initial)
    val state
        get() = _state.asStateFlow()

    fun update(block: T.() -> T) {
        _state.update(block)
    }
}
