package com.githukudenis.comlib.feature.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileListItem(
    clickable: Boolean = true,
    onClick: (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(clickable) {
                onClick?.invoke()
            }
            .padding(vertical = 12.dp)
        , verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            if (leading != null) {
                leading()
            }
            Spacer(modifier = Modifier.width(12.dp))
            title()
        }
        if (subtitle != null) {
            subtitle()
        }
        if (trailing != null) {
            trailing()
        }
    }
}