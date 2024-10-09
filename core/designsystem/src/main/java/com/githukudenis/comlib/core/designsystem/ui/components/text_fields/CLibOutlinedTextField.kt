
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
package com.githukudenis.comlib.core.designsystem.ui.components.text_fields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import com.githukudenis.comlib.core.designsystem.ui.theme.Critical

@Composable
fun CLibOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    maxLines: Int = 1,
    minLines: Int = 1,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    colors: TextFieldColors =
        TextFieldDefaults.colors(
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            errorCursorColor = Critical,
            errorLabelColor = Critical,
            errorTrailingIconColor = Critical,
            errorIndicatorColor = Critical,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        ),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = shape,
        colors = colors,
        label = {
            if (label != null) {
                Text(text = label, style = MaterialTheme.typography.labelMedium)
            }
        },
        placeholder = placeholder,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        isError = isError,
        modifier = modifier,
        maxLines = maxLines,
        minLines = minLines,
        readOnly = readOnly,
        singleLine = singleLine
    )
}
