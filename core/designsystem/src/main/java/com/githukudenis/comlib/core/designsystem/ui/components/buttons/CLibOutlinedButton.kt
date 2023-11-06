package com.githukudenis.comlib.core.designsystem.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun CLibOutlinedButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.onSurface,
    ),
    border: BorderStroke = BorderStroke(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onBackground.copy(0.9f)
    ),
    shape: Shape = MaterialTheme.shapes.small,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        content = content,
        border = border,
        colors = colors,
        shape = shape,
        modifier = modifier
    )
}