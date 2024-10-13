
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.githukudenis.comlib.core.designsystem.R
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CLibAlertContentDialog(
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit
) {
    val animateTrigger = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) { launch { animateTrigger.value = true } }
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            scope.launch {
                animateTrigger.value = false
                delay(100)
                onDismissRequest()
            }
        }
    ) {
        AnimatedScaleInTransition(visible = animateTrigger.value) {
            Box(
                modifier =
                    Modifier.fillMaxWidth(fraction = 0.8f)
                        .wrapContentHeight()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = MaterialTheme.shapes.large
                        ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(LocalDimens.current.extraLarge),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(LocalDimens.current.extraLarge)
                ) {
                    if (icon != null) {
                        icon()
                    }
                    title()
                    content()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = onDismissRequest) {
                            Text(text = stringResource(id = R.string.confirm))
                        }
                    }
                }
            }
        }
    }
}
