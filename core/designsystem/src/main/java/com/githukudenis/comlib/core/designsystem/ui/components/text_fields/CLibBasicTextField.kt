package com.githukudenis.comlib.core.designsystem.ui.components.text_fields

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CLibBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = MaterialTheme.typography.labelSmall,
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        )
    )
}