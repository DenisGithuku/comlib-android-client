
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
package com.githukudenis.comlib.feature.edit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PhotoSizeSelectActual
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibLoadingDialog
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.components.text_fields.CLibBasicTextField
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.profile.R
import com.githukudenis.comlib.feature.profile.components.ProfileImage

@Composable
fun EditProfileScreen(onNavigateUp: () -> Unit, viewModel: EditProfileViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    EditProfileContent(
        state = state,
        onNavigateUp = onNavigateUp,
        onSaveChanges = viewModel::updateUser,
        onChangeFirstname = viewModel::onChangeFirstname,
        onChangeLastname = viewModel::onChangeLastname,
        onChangeUsername = viewModel::onChangeUsername,
        onChangeUserImage = viewModel::onChangePhoto
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(
    state: EditProfileUiState,
    onNavigateUp: () -> Unit,
    onSaveChanges: () -> Unit,
    onChangeFirstname: (String) -> Unit,
    onChangeLastname: (String) -> Unit,
    onChangeUsername: (String) -> Unit,
    onChangeUserImage: (Uri) -> Unit
) {
    var sheetIsOpen by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedProfileItem by rememberSaveable { mutableStateOf(ProfileItem.NOTHING) }

    if (state.isUpdating) {
        CLibLoadingDialog {}
    }

    val imagePickLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                onChangeUserImage(uri)
            } else {
                Toast.makeText(context, context.getString(R.string.no_media_selected), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.screen_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            LoadingContent()
        }

        if (sheetIsOpen) {
            ModalBottomSheet(
                onDismissRequest = { sheetIsOpen = false },
                //                windowInsets = WindowInsets.ime
                contentWindowInsets = { WindowInsets.ime }
            ) {
                when (selectedProfileItem) {
                    ProfileItem.NOTHING -> {
                        Unit
                    }
                    ProfileItem.FIRSTNAME -> {
                        state.firstname?.let {
                            EditableProfileItem(
                                value = it,
                                onValueChange = onChangeFirstname,
                                onSaveChanges = {
                                    onSaveChanges()
                                    sheetIsOpen = false
                                },
                                onCancel = { sheetIsOpen = false }
                            )
                        }
                    }
                    ProfileItem.LASTNAME -> {
                        state.lastname?.let {
                            EditableProfileItem(
                                value = it,
                                onValueChange = onChangeLastname,
                                onSaveChanges = {
                                    onSaveChanges()
                                    sheetIsOpen = false
                                },
                                onCancel = { sheetIsOpen = false }
                            )
                        }
                    }
                    ProfileItem.USERNAME -> {
                        state.username?.let {
                            EditableProfileItem(
                                value = it,
                                onValueChange = onChangeUsername,
                                onSaveChanges = {
                                    onSaveChanges()
                                    sheetIsOpen = false
                                },
                                onCancel = { sheetIsOpen = false }
                            )
                        }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ProfileImage(
                    imageUrl = state.profileUrl,
                    size = 200.dp,
                    onChangeImage = {
                        imagePickLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
            }
            item {
                state.username?.let {
                    EditProfileItem(
                        title = stringResource(id = R.string.username_label),
                        value = state.username,
                        icon = Icons.Outlined.AccountCircle,
                        profileItem = ProfileItem.USERNAME
                    ) {
                        selectedProfileItem = it
                        sheetIsOpen = true
                    }
                }
            }
            item {
                state.firstname?.let {
                    EditProfileItem(
                        title = stringResource(id = R.string.firstname_label),
                        value = state.firstname,
                        icon = Icons.Outlined.AccountCircle,
                        profileItem = ProfileItem.FIRSTNAME,
                        onClick = {
                            selectedProfileItem = it
                            sheetIsOpen = true
                        }
                    )
                }
            }
            item {
                state.lastname?.let {
                    EditProfileItem(
                        title = stringResource(id = R.string.lastname_label),
                        value = state.lastname,
                        icon = Icons.Outlined.AccountCircle,
                        profileItem = ProfileItem.LASTNAME,
                        onClick = {
                            selectedProfileItem = it
                            sheetIsOpen = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EditProfileItem(
    title: String,
    value: String,
    description: String? = null,
    icon: ImageVector,
    profileItem: ProfileItem,
    onClick: (ProfileItem) -> Unit
) {
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .clickable { onClick(profileItem) }
                .padding(LocalDimens.current.extraLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.extraLarge)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Column(verticalArrangement = Arrangement.spacedBy(LocalDimens.current.small)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                Text(text = value, style = MaterialTheme.typography.bodyMedium)
                if (description != null) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
            }
        }
        IconButton(onClick = { onClick(profileItem) }) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = stringResource(id = R.string.edit),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun ImageChooser(onOpenGallery: () -> Unit, onOpenCamera: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ImageSourceComponent(
            onClick = onOpenCamera,
            icon = Icons.Outlined.CameraAlt,
            label = stringResource(id = R.string.camera_label)
        )
        ImageSourceComponent(
            onClick = onOpenGallery,
            icon = Icons.Outlined.PhotoSizeSelectActual,
            label = stringResource(id = R.string.gallery_label)
        )
    }
}

@Composable
private fun ImageSourceComponent(label: String, onClick: () -> Unit, icon: ImageVector) {
    Column(
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier =
                Modifier.size(60.dp)
                    .clickable { onClick() }
                    .border(
                        width = 0.8.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        shape = CircleShape
                    ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CLibCircularProgressBar()
    }
}

@Composable
fun EditableProfileItem(
    value: String,
    onValueChange: (String) -> Unit,
    onSaveChanges: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.extraLarge),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
    ) {
        CLibBasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            onDone = onSaveChanges
        )
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
        ) {
            CLibTextButton(onClick = onCancel) {
                Text(
                    text = stringResource(id = R.string.cancel_label),
                    style = MaterialTheme.typography.labelSmall
                )
            }
            CLibTextButton(onClick = onSaveChanges) {
                Text(
                    text = stringResource(id = R.string.save_label),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

enum class ProfileItem {
    NOTHING,
    FIRSTNAME,
    LASTNAME,
    USERNAME
}
