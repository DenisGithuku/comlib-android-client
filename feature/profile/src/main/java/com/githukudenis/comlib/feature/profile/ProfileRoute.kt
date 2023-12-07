package com.githukudenis.comlib.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.feature.profile.components.ProfileImage
import com.githukudenis.comlib.feature.profile.components.ProfileListItem

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onOpenMyBooks: () -> Unit,
    onSignOut: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onSignedOut by rememberUpdatedState(newValue = onSignOut)

    LaunchedEffect(key1 = state.isSignedOut) {
        if (state.isSignedOut) {
            onSignedOut()
        }
    }
    ProfileScreen(
        state = state,
        onBackPressed = onBackPressed,
        onOpenMyBooks = onOpenMyBooks,
        onSignOut = viewModel::onSignOut
    )
}

@Composable
private fun ProfileScreen(
    state: ProfileUiState,
    onBackPressed: () -> Unit,
    onOpenMyBooks: () -> Unit,
    onSignOut: () -> Unit
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding(),
                    bottom = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateBottomPadding(),
                )
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = "Profile", style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(imageUrl = "https://comlib-api.onrender.com/img/users/${state.profile?.imageUrl}",
                onChangeImage = {})
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "${state.profile?.firstname?.capitalize()} ${state.profile?.lastname?.capitalize()}",
                    style = MaterialTheme.typography.titleSmall
                )
                state.profile?.email?.let {
                    Text(
                        text = state.profile.email, style = MaterialTheme.typography.bodyMedium
                    )
                }
                CLibButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Edit", style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileListItem(
            leading = Icons.Default.MenuBook,
            onClick = onOpenMyBooks,
            title = stringResource(R.string.my_books),
        )
        ProfileListItem(
            leading = Icons.Default.FavoriteBorder,
            onClick = {},
            title = stringResource(R.string.favourites)
        )
        ProfileListItem(
            leading = Icons.Default.WbSunny, onClick = {}, title = stringResource(R.string.display)
        )
        ProfileListItem(
            leading = Icons.Default.NotificationsNone,
            onClick = {},
            title = stringResource(R.string.notifications)
        )
        Divider(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ProfileListItem(leading = Icons.Default.DeleteOutline,
            title = stringResource(R.string.clear_cache),
            onClick = {})
        ProfileListItem(
            leading = Icons.Default.Logout, title = stringResource(R.string.logout),

            onClick = onSignOut
        )
    }
}