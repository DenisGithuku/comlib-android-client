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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    viewModel: ProfileViewModel = hiltViewModel(), onBackPressed: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        state = state, onBackPressed = onBackPressed
    )
}

@Composable
private fun ProfileScreen(state: ProfileUiState, onBackPressed: () -> Unit) {
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
                    start = 16.dp,
                    end = 16.dp
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
            modifier = Modifier.fillMaxWidth()
        ) {
            ProfileImage(imageUrl = state.profile?.imageUrl, onChangeImage = {})
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "${state.profile?.firstname?.capitalize()} ${state.profile?.lastname?.capitalize()}",
                    style = MaterialTheme.typography.titleLarge
                )
                state.profile?.email?.let {
                    Text(
                        text = state.profile.email, style = MaterialTheme.typography.bodyMedium
                    )
                }
                CLibButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Edit"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ProfileListItem(leading = {
            Icon(
                imageVector = Icons.Default.FavoriteBorder, contentDescription = null
            )
        }, title = {
            Text(
                text = stringResource(R.string.favourites)
            )
        }, trailing = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(R.string.open)
            )
        })
        ProfileListItem(leading = {
            Icon(
                imageVector = Icons.Default.WbSunny, contentDescription = null
            )
        }, title = {
            Text(
                text = stringResource(R.string.display)
            )
        }, trailing = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(R.string.open)
            )
        })
        ProfileListItem(leading = {
            Icon(
                imageVector = Icons.Default.NotificationsNone, contentDescription = null
            )
        }, title = {
            Text(
                text = stringResource(R.string.notifications)
            )
        }, trailing = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(R.string.open)
            )
        })
        Divider(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        ProfileListItem(leading = {
            Icon(
                imageVector = Icons.Default.DeleteOutline, contentDescription = null
            )
        }, title = {
            Text(
                text = stringResource(R.string.clear_cache)
            )
        }, trailing = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = stringResource(R.string.open)
            )
        })
        ProfileListItem(
            leading = {
                Icon(
                    imageVector = Icons.Default.Logout, contentDescription = null
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.logout)
                )
            },
        )
    }
}