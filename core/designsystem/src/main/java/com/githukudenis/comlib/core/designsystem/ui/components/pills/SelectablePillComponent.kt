package com.githukudenis.comlib.core.designsystem.ui.components.pills

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.githukudenis.comlib.core.designsystem.R
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun SelectablePillComponent(
    modifier: Modifier = Modifier,
    value: String,
    id: String,
    isSelected: Boolean,
    hasIcon: Boolean = true,
    onToggleSelection: (String) -> Unit
) {

    Surface(shape = CircleShape,
        border = BorderStroke(
            width = if (isSelected) 0.dp else 1.dp,
            color = if (isSelected) Color.Transparent else MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.1f
            )
        ),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        onClick = { onToggleSelection(id) }) {
        Row(
            modifier = Modifier.padding(LocalDimens.current.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.small)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )
            if (isSelected && hasIcon) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.selection_indicator),
                    tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.7f
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun SelectableGenrePreview() {
    SelectablePillComponent(
        value = "Self help",
        isSelected = false,
        hasIcon = true,
        id = "",
        onToggleSelection = {})
}