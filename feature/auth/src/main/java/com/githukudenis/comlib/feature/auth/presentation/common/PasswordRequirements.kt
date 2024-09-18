
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
package com.githukudenis.comlib.feature.auth.presentation.common

import androidx.annotation.StringRes
import com.githukudenis.comlib.feature.auth.R

enum class PasswordRequirements(@StringRes val label: Int) {
    CapitalLetter(R.string.password_requirement_capital),
    Number(R.string.password_requirement_digit),
    SpecialCharacter(R.string.password_requirement_special),
    Length(R.string.password_requirement_length)
}
