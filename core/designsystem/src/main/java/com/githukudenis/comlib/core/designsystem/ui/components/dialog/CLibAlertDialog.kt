package com.githukudenis.comlib.core.designsystem.ui.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.githukudenis.comlib.core.designsystem.R
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton

@Composable
fun CLibAlertDialog(
    title: String, text: String, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    AlertDialog(title = {
        Text(
            text = title
        )
    }, text = {
        Text(text = text)
    }, icon = {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface.copy(0.7f)
        )
    }, onDismissRequest = onDismiss, confirmButton = {
        CLibTextButton(
            onClick = onConfirm
        ) {
            Text(
                text = stringResource(R.string.confirm)
            )
        }
    }, dismissButton = {
        CLibTextButton(
            onClick = onDismiss
        ) {
            Text(
                text = stringResource(id = R.string.cancel)
            )
        }
    })
}