package com.githukudenis.comlib.feature.auth.presentation.complete_profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibLoadingDialog
import com.githukudenis.comlib.core.designsystem.ui.components.text_fields.CLibOutlinedTextField
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.auth.R
import kotlinx.coroutines.delay

@Composable
fun CompleteProfileRoute(
    viewModel: CompleteProfileViewModel = hiltViewModel(),
    onUpdateComplete: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    CompleteProfileScreen(
        state = state,
        onSelectImage = viewModel::onSelectImage,
        onSelectUserName = viewModel::onSelectUserName,
        onUpdateComplete = onUpdateComplete,
        onSubmit = viewModel::onSubmit,
        onDismissUserMessage = viewModel::onDismissUserMessage
    )
}

@Composable
internal fun CompleteProfileScreen(
    state: CompleteProfileUiState,
    onSelectImage: (Uri) -> Unit,
    onSelectUserName: (String) -> Unit,
    onUpdateComplete: () -> Unit,
    onSubmit: () -> Unit,
    onDismissUserMessage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalDimens.current.sixteen),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        if (state.isLoading) {
            CLibLoadingDialog(
                label = stringResource(id = R.string.update_label)
            )
        }

        val updateComplete by rememberUpdatedState(onUpdateComplete)

        LaunchedEffect(state.userMessages, state.isSuccess) {
            if (state.userMessages.isNotEmpty()) {
                Toast.makeText(
                    context, state.userMessages.first().message, Toast.LENGTH_SHORT
                ).show()
                delay(3000)
                onDismissUserMessage()
                if (state.isSuccess) {
                    updateComplete()
                }
            }
        }

        Title()

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            ImageComponent(uri = state.selectedImageUri, onSelectImage = onSelectImage)

            Spacer(modifier = Modifier.height(LocalDimens.current.twentyFour))

            UsernameComponent(
                username = state.userName,
                onUsernameChange = onSelectUserName
            )
        }

        CLibButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSubmit,
            enabled = state.isUiValid
        ) {
            Text(
                text = stringResource(id = R.string.complete_profile_btn_txt),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(4.dp)
            )
        }

    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(id = R.string.complete_profile_title),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ImageComponent(
    uri: Uri?, onSelectImage: (Uri) -> Unit
) {
    val context = LocalContext.current
    val imagePickLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                onSelectImage(uri)
            } else {
                Toast.makeText(
                    context, context.getString(R.string.no_media_selected), Toast.LENGTH_SHORT
                ).show()
            }
        }

    Box {
        if (uri != null) {
            AsyncImage(model = uri,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable {
                        imagePickLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    })
        } else {
            Box(modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.08f))
                .clickable {
                    imagePickLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                })
        }

        IconButton(
            colors =  IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier.align(
                Alignment.BottomEnd
            ),
            onClick = {
                imagePickLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = stringResource(id = R.string.edit),
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun UsernameComponent(
    username: String,
    onUsernameChange: (String) -> Unit,
) {
    CLibOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = username,
        label = stringResource(id = R.string.username_label),
        onValueChange = onUsernameChange
    )
}