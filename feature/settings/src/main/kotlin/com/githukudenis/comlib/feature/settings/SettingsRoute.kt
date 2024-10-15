
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

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibAlertContentDialog
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibAlertDialog
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.core.model.UserProfileData
import com.githukudenis.comlib.feature.settings.components.AppearanceItem
import com.githukudenis.comlib.feature.settings.components.SettingListItem
import com.githukudenis.comlib.feature.settings.components.ToggleAbleAppearanceItem

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onOpenEditProfile: () -> Unit,
    onOpenMyBooks: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    SettingsScreen(
        state = state,
        onNavigateUp = onNavigateUp,
        onChangeImage = {},
        onEditProfile = onOpenEditProfile,
        onToggleAppearanceSheet = { viewModel.onEvent(SettingsUiEvent.ToggleAppearance(it)) },
        onToggleClearCache = { viewModel.onEvent(SettingsUiEvent.ToggleClearCache(it)) },
        onOpenMyBooks = onOpenMyBooks,
        onOpenPrivacyPolicy = onOpenPrivacyPolicy,
        onClearCache = {
            if (context.cacheDir.deleteRecursively()) {
                Toast.makeText(context, "Cache cleared", Toast.LENGTH_SHORT).show()
                viewModel.onEvent(SettingsUiEvent.ToggleClearCache(false))
            } else {
                Toast.makeText(context, "Failed to clear cache", Toast.LENGTH_SHORT).show()
            }
        },
        onChangeTheme = { viewModel.onEvent(SettingsUiEvent.ChangeTheme(it)) },
        onToggleThemeDialog = { viewModel.onEvent(SettingsUiEvent.ToggleThemeDialog(it)) },
        onToggleNotifications = { viewModel.onEvent(SettingsUiEvent.ToggleNotifications(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onNavigateUp: () -> Unit,
    onChangeImage: () -> Unit,
    onEditProfile: () -> Unit,
    onOpenMyBooks: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit,
    onClearCache: () -> Unit,
    onToggleAppearanceSheet: (Boolean) -> Unit,
    onToggleThemeDialog: (Boolean) -> Unit,
    onToggleClearCache: (Boolean) -> Unit,
    onChangeTheme: (ThemeConfig) -> Unit,
    onToggleNotifications: (Boolean) -> Unit
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
        if (state.uiComponentsState.isCacheDialogVisible) {
            CLibAlertDialog(
                title = stringResource(id = R.string.clear_cache_dialog_title),
                text = stringResource(id = R.string.clear_cache_dialog_text),
                onDismiss = { onToggleClearCache(false) },
                onConfirm = { onClearCache() }
            )
        }

        if (state.uiComponentsState.isAppearanceSheetVisible) {
            ModalBottomSheet(onDismissRequest = { onToggleAppearanceSheet(false) }) {
                AppearanceItem(
                    title = stringResource(R.string.theme_title),
                    icon = R.drawable.ic_appearance,
                    label = state.selectedTheme.name.lowercase().replaceFirstChar { it.uppercase() },
                    onClick = { onToggleThemeDialog(true) }
                )
                ToggleAbleAppearanceItem(
                    title = stringResource(R.string.notifications),
                    icon = R.drawable.ic_notifications,
                    isToggled = state.isNotificationsToggled,
                    onToggle = onToggleNotifications
                )
            }
        }

        if (state.uiComponentsState.isThemeDialogVisible) {
            CLibAlertContentDialog(
                title = {
                    Text(
                        text = stringResource(R.string.select_theme_dialog_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                content = {
                    Column {
                        state.availableThemes.forEach { availableTheme ->
                            Row(
                                modifier = Modifier.clickable { onChangeTheme(availableTheme) }.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RadioButton(
                                    selected = availableTheme == state.selectedTheme,
                                    onClick = { onChangeTheme(availableTheme) }
                                )
                                Text(
                                    text = availableTheme.name.lowercase().replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                },
                onDismissRequest = { onToggleThemeDialog(false) }
            )
        }

        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            ProfileSection(userProfileData = state.userProfileData, onChangeImage = onChangeImage)
            SettingListItem(
                icon = R.drawable.ic_person,
                onClick = onEditProfile,
                title = stringResource(id = R.string.account_title),
                description = stringResource(id = R.string.account_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_library,
                onClick = onOpenMyBooks,
                title = stringResource(id = R.string.library_title),
                description = stringResource(id = R.string.library_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_appearance,
                onClick = { onToggleAppearanceSheet(true) },
                title = stringResource(id = R.string.general_title),
                description = stringResource(id = R.string.general_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_shield,
                onClick = onOpenPrivacyPolicy,
                title = stringResource(id = R.string.support_title),
                description = stringResource(id = R.string.support_desc)
            )
            SettingListItem(
                icon = R.drawable.ic_management,
                onClick = { onToggleClearCache(true) },
                title = stringResource(id = R.string.app_management_title),
                description = stringResource(id = R.string.app_management_desc)
            )
        }
    }
}

@Composable
fun ProfileSection(userProfileData: UserProfileData, onChangeImage: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.sixteen),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(imagePath = userProfileData.profilePicturePath, onChangeImage = onChangeImage)
        Spacer(modifier = Modifier.width(LocalDimens.current.extraLarge))
        Column(modifier = Modifier) {
            Text(
                text =
                    "${userProfileData.firstname?.capitalize()} ${userProfileData.lastname?.capitalize()}",
                style = MaterialTheme.typography.titleSmall
            )
            userProfileData.email?.let { email ->
                Text(text = email, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// @Composable
// fun ProfileSection(profileItemState: ProfileItemState, onChangeImage: () -> Unit) {
//    when (profileItemState) {
//        is ProfileItemState.Error -> Text(text = profileItemState.message)
//        ProfileItemState.Loading -> ProfileLoader()
//        is ProfileItemState.Success ->
//            ProfileLoaded(profile = profileItemState.profile, onChangeImage = onChangeImage)
//    }
// }

@Composable
fun ProfileLoader() {
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
fun ProfileLoaded(profile: Profile, onChangeImage: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.sixteen),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileImage(imagePath = profile.imageUrl, onChangeImage = onChangeImage)
        Spacer(modifier = Modifier.width(LocalDimens.current.extraLarge))
        Column(modifier = Modifier) {
            Text(
                text = "${profile.firstname?.capitalize()} ${profile.lastname?.capitalize()}",
                style = MaterialTheme.typography.titleSmall
            )
            profile.email?.let { Text(text = profile.email, style = MaterialTheme.typography.bodyMedium) }
        }
    }
}

@Composable
fun ProfileImage(imagePath: String?, size: Dp = 80.dp, onChangeImage: () -> Unit) {
    Box(modifier = Modifier) {
        //        AsyncImage(
        //            model = imageUrl,
        //            modifier = Modifier.size(size).clip(CircleShape),
        //            contentScale = ContentScale.Crop,
        //            contentDescription = null,
        //            placeholder = painterResource(id = R.drawable.ic_profile_placeholder)
        //        )

        Image(
            modifier = Modifier.size(size).clip(CircleShape),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(imagePath),
            contentDescription = "User profile"
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
