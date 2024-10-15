
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
package com.githukudenis.comlib.core.designsystem.ui.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.githukudenis.comlib.core.designsystem.R
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton

@Composable
fun CLibAlertDialog(title: String, text: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        title = { Text(text = title) },
        text = { Text(text = text) },
        icon = {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(0.7f)
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            CLibTextButton(onClick = onConfirm) { Text(text = stringResource(R.string.confirm)) }
        },
        dismissButton = {
            CLibTextButton(onClick = onDismiss) { Text(text = stringResource(id = R.string.cancel)) }
        }
    )
}
