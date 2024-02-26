package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun ErrorCard(
    content: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalDimens.current.large),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ErrorText(
            text = content
        )
    }
}

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
    )
}