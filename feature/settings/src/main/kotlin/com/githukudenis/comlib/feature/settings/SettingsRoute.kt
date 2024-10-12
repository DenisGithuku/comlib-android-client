
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.settings.components.SettingListItem

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onOpenEditProfile: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    SettingsScreen(
        state = state,
        onNavigateUp = onNavigateUp,
        onChangeImage = {},
        onEditProfile = onOpenEditProfile
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onNavigateUp: () -> Unit,
    onChangeImage: () -> Unit,
    onEditProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            ProfileSection(
                profileItemState = state.profileItemState,
                onChangeImage = onChangeImage,
                onEditProfile = onEditProfile
            )
            SettingListItem(
                icon = R.drawable.ic_person,
                onClick = {},
                title = stringResource(id = R.string.account_title),
                description = stringResource(id = R.string.account_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_library,
                onClick = {},
                title = stringResource(id = R.string.library_title),
                description = stringResource(id = R.string.library_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_appearance,
                onClick = {},
                title = stringResource(id = R.string.general_title),
                description = stringResource(id = R.string.general_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_shield,
                onClick = {},
                title = stringResource(id = R.string.support_title),
                description = stringResource(id = R.string.support_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_management,
                onClick = {},
                title = stringResource(id = R.string.app_management_title),
                description = stringResource(id = R.string.app_management_desc)
            )
        }
    }
}

@Composable
fun ProfileSection(
    profileItemState: ProfileItemState,
    onChangeImage: () -> Unit,
    onEditProfile: () -> Unit
) {
    when (profileItemState) {
        is ProfileItemState.Error -> Text(text = profileItemState.message)
        ProfileItemState.Loading -> ProfileLoader()
        is ProfileItemState.Success ->
            ProfileLoaded(
                profile = profileItemState.profile,
                onChangeImage = onChangeImage,
                onEditProfile = onEditProfile
            )
    }
}

@Composable
fun ProfileLoader(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.sixteen),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CLibCircularProgressBar()
        Spacer(modifier = Modifier.height(LocalDimens.current.extraLarge))
        Text(text = stringResource(R.string.fetching_profile_indicator))
    }
}

@Composable
fun ProfileLoaded(profile: Profile, onChangeImage: () -> Unit, onEditProfile: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.sixteen)) {
        ProfileImage(imageUrl = profile.imageUrl, onChangeImage = onChangeImage)
        Spacer(modifier = Modifier.width(LocalDimens.current.extraLarge))
        Column(modifier = Modifier) {
            Text(
                text = "${profile.firstname?.capitalize()} ${profile.lastname?.capitalize()}",
                style = MaterialTheme.typography.titleSmall
            )
            profile.email?.let { Text(text = profile.email, style = MaterialTheme.typography.bodyMedium) }
            CLibOutlinedButton(onClick = onEditProfile) {
                Text(text = "Edit", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun ProfileImage(imageUrl: String?, size: Dp = 100.dp, onChangeImage: () -> Unit) {
    Box(modifier = Modifier) {
        AsyncImage(
            model = imageUrl,
            modifier = Modifier.size(size).clip(CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_profile_placeholder)
        )
        Box(
            modifier =
                Modifier.align(alignment = Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                    .border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.background, shape = CircleShape)
                    .clickable { onChangeImage() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.padding(4.dp),
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = stringResource(R.string.change_image),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}
