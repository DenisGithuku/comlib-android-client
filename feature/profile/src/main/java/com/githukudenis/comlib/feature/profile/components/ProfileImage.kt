package com.githukudenis.comlib.feature.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.githukudenis.comlib.feature.profile.R

@Composable
fun ProfileImage(
    imageUrl: String?, onChangeImage: () -> Unit
) {
    Box(modifier = Modifier) {
        AsyncImage(
            model = imageUrl,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_profile_placeholder),
        )
        Box(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                .padding(2.dp)
                .clickable { onChangeImage() }
                .border(
                    width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f), shape = CircleShape
                )
                .background(MaterialTheme.colorScheme.background, shape = CircleShape)
                , contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = stringResource(R.string.change_image),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileImagePrev() {
    ProfileImage(imageUrl = "") {

    }
}