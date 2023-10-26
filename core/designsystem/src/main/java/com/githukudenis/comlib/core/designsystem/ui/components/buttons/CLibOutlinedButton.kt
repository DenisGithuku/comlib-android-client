package com.githukudenis.comlib.core.designsystem.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun CLibOutlinedButton(
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.onSurface
    ),
    border: BorderStroke = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.background.copy(0.9f)
    ),
    shape: Shape = MaterialTheme.shapes.small,
    content: @Composable RowScope.() -> Unit,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        content = content,
        border = border,
        colors = colors,
        shape = shape
    )
}