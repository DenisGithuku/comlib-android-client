package com.githukudenis.comlib.feature.genre_setup

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
import androidx.compose.ui.unit.dp
import com.githukudenis.comlib.core.common.untangle
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun SelectableGenreComponent(
    modifier: Modifier = Modifier,
    selectableGenreItem: SelectableGenreItem,
    onToggleSelection: (String) -> Unit
) {

    Surface(shape = CircleShape,
        border = BorderStroke(
            width = if (selectableGenreItem.isSelected) 0.dp else 1.dp,
            color = if (selectableGenreItem.isSelected) Color.Transparent else MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.1f
            )
        ),
        color = if (selectableGenreItem.isSelected) MaterialTheme.colorScheme.primary.copy(
            alpha = 0.1f
        ) else Color.Transparent,
        onClick = { onToggleSelection(selectableGenreItem.genre.id) }) {
        Row(
            modifier = Modifier.padding(LocalDimens.current.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.small)
        ) {
            Text(
                text = selectableGenreItem.genre.name.untangle("-"),
                style = MaterialTheme.typography.bodyMedium,
                color = if (selectableGenreItem.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )
            if (selectableGenreItem.isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.selection_indicator),
                    tint = if (selectableGenreItem.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.7f
                    )
                )
            }
        }
    }

}