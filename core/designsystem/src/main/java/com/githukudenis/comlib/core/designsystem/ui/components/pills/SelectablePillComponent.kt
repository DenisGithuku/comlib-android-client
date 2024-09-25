
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
package com.githukudenis.comlib.core.designsystem.ui.components.pills

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun SelectablePillComponent(
    value: String,
    id: String,
    isSelected: Boolean,
    @DrawableRes icon: Int? = null,
    onToggleSelection: (String) -> Unit
) {
    Surface(
        shape = CircleShape,
        border =
            BorderStroke(
                width = if (isSelected) 0.dp else 1.dp,
                color =
                    if (isSelected) {
                        Color.Transparent
                    } else MaterialTheme.colorScheme.onSecondaryContainer.copy(0.4f)
            ),
        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
        onClick = { onToggleSelection(id) }
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color =
                if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier =
                Modifier.padding(
                    vertical = LocalDimens.current.eight,
                    horizontal = LocalDimens.current.twelve
                )
        )
        if (icon != null) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview
@Composable
private fun SelectableGenrePreview() {
    SelectablePillComponent(value = "Self help", isSelected = true, id = "", onToggleSelection = {})
}
