
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
package com.githukudenis.comlib.feature.profile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibAlertContentDialog
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibAlertDialog
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.feature.profile.components.ProfileImage
import com.githukudenis.comlib.feature.profile.components.ProfileListItem

@Composable
fun ProfileRoute(
    versionName: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onOpenMyBooks: () -> Unit,
    onSignOut: () -> Unit,
    onEditProfile: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val onSignedOut by rememberUpdatedState(newValue = onSignOut)
    LaunchedEffect(key1 = state.isSignedOut) {
        if (state.isSignedOut) {
            Toast.makeText(context, "You have been signed out", Toast.LENGTH_SHORT).show()
            onSignedOut()
        }
    }

    val imagePickLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.onEvent(ProfileUiEvent.ChangeUserImage(uri))
            } else {
                Toast.makeText(context, context.getString(R.string.no_media_selected), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    ProfileScreen(
        state = state,
        versionName = versionName,
        onNavigateUp = onBackPressed,
        onOpenMyBooks = onOpenMyBooks,
        onSignOut = { viewModel.onEvent(ProfileUiEvent.SignOut) },
        onToggleCacheDialog = { viewModel.onEvent(ProfileUiEvent.ToggleCache(it)) },
        onClearCache = {
            if (context.cacheDir.deleteRecursively()) {
                Toast.makeText(context, "Cache cleared", Toast.LENGTH_SHORT).show()
                viewModel.onEvent(ProfileUiEvent.ToggleCache(false))
            } else {
                Toast.makeText(context, "Failed to clear cache", Toast.LENGTH_SHORT).show()
            }
        },
        onToggleSignOutDialog = { viewModel.onEvent(ProfileUiEvent.ToggleSignOut(it)) },
        onEditProfile = onEditProfile,
        onChangeImage = {
            imagePickLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onChangeTheme = { viewModel.onEvent(ProfileUiEvent.ChangeTheme(it)) },
        onToggleThemeDialog = { viewModel.onEvent(ProfileUiEvent.ToggleThemeDialog(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    state: ProfileUiState,
    versionName: String,
    onNavigateUp: () -> Unit,
    onOpenMyBooks: () -> Unit,
    onSignOut: () -> Unit,
    onToggleCacheDialog: (Boolean) -> Unit,
    onClearCache: () -> Unit,
    onToggleSignOutDialog: (Boolean) -> Unit,
    onEditProfile: () -> Unit,
    onChangeImage: () -> Unit,
    onChangeTheme: (ThemeConfig) -> Unit,
    onToggleThemeDialog: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CLibCircularProgressBar()
            }
            return@Scaffold
        }

        if (state.isThemeDialogOpen) {
            CLibAlertContentDialog(
                title = { Text(text = "Select theme", style = MaterialTheme.typography.headlineSmall) },
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

        if (state.isClearCache) {
            CLibAlertDialog(
                title = stringResource(id = R.string.clear_cache_dialog_title),
                text = stringResource(id = R.string.clear_cache_dialog_text),
                onDismiss = { onToggleCacheDialog(false) },
                onConfirm = { onClearCache() }
            )
        }

        if (state.isSignout) {
            CLibAlertDialog(
                title = stringResource(id = R.string.sign_out_dialog_title),
                text = stringResource(id = R.string.sign_out_dialog_text),
                onDismiss = { onToggleSignOutDialog(false) },
                onConfirm = { onSignOut() }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                ProfileSection(
                    profileItemState = state.profileItemState,
                    onChangeImage = onChangeImage,
                    onEditProfile = onEditProfile
                )
                Spacer(modifier = Modifier.height(LocalDimens.current.extraLarge))
                Card(
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                ) {
                    ProfileListItem(
                        leading = Icons.AutoMirrored.Filled.MenuBook,
                        onClick = onOpenMyBooks,
                        title = stringResource(R.string.my_books)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge),
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                    )
                    //                    ProfileListItem(
                    //                        leading = Icons.Default.FavoriteBorder,
                    //                        onClick = {},
                    //                        title = stringResource(R.string.favourites)
                    //                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge),
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                    )
                    ProfileListItem(
                        leading = Icons.Default.WbSunny,
                        onClick = { onToggleThemeDialog(true) },
                        title = stringResource(R.string.display)
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge),
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                    )
                    //                    ProfileListItem(
                    //                        leading = Icons.Default.NotificationsNone,
                    //                        onClick = {},
                    //                        title = stringResource(R.string.notifications)
                    //                    )
                }
                Spacer(modifier = Modifier.height(LocalDimens.current.extraLarge))
                Card(
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                ) {
                    ProfileListItem(
                        leading = Icons.Default.DeleteOutline,
                        title = stringResource(R.string.clear_cache),
                        onClick = { onToggleCacheDialog(true) }
                    )
                    HorizontalDivider(
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                    )
                    ProfileListItem(
                        leading = Icons.AutoMirrored.Filled.Logout,
                        title = stringResource(R.string.logout),
                        onClick = { onToggleSignOutDialog(true) }
                    )
                }
            }
            Text(
                text = "Version $versionName",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth().padding(bottom = LocalDimens.current.extraLarge),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
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
