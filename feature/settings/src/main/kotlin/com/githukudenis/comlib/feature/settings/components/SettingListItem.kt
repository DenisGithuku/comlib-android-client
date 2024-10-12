package com.githukudenis.comlib.feature.settings.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun SettingListItem(
    @DrawableRes icon: Int, onClick: () -> Unit, title: String, description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(LocalDimens.current.sixteen),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = icon), contentDescription = title
        )
        Spacer(modifier = Modifier.width(LocalDimens.current.sixteen))
        Column(
            modifier = Modifier,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(LocalDimens.current.eight))
            Text(
                text = description,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                ),
            )
        }
    }
}