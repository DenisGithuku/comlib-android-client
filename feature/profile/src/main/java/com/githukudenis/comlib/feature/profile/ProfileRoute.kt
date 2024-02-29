package com.githukudenis.comlib.feature.profile

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibLoadingSpinner
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibAlertDialog
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.profile.components.ProfileImage
import com.githukudenis.comlib.feature.profile.components.ProfileListItem

@Composable
fun ProfileRoute(
    versionName: String,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onOpenMyBooks: () -> Unit,
    onSignOut: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onSignedOut by rememberUpdatedState(newValue = onSignOut)
    val context = LocalContext.current
    LaunchedEffect(key1 = state.isSignedOut) {
        if (state.isSignedOut) {
            Toast.makeText(context, "You have been signed out", Toast.LENGTH_SHORT).show()
            onSignedOut()
        }
    }
    ProfileScreen(state = state,
        versionName = versionName,
        onNavigateUp = onBackPressed,
        onOpenMyBooks = onOpenMyBooks,
        onSignOut = viewModel::onSignOut,
        onToggleCacheDialog = viewModel::toggleDialog,
        onClearCache = {
            if (context.cacheDir.deleteRecursively()) {
                Toast.makeText(context, "Cache cleared", Toast.LENGTH_SHORT).show()
                viewModel.toggleDialog(false)
            } else {
                Toast.makeText(context, "Failed to clear cache", Toast.LENGTH_SHORT).show()
            }
        })
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
    onClearCache: () -> Unit
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(R.string.profile_title), style = MaterialTheme.typography.titleMedium
            )
        }, navigationIcon = {
            IconButton(onClick = { onNavigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back"
                )
            }
        })
    }) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CLibLoadingSpinner()
            }
            return@Scaffold
        }

        if (state.isDialogVisible) {
            CLibAlertDialog(title = stringResource(id = R.string.clear_cache_dialog_title),
                text = stringResource(
                    id = R.string.clear_cache_dialog_text
                ),
                onDismiss = { onToggleCacheDialog(false) },
                onConfirm = {
                    onClearCache()
                })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(
                    innerPadding
                ), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = LocalDimens.current.extraLarge),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileImage(imageUrl = "https://comlib-api.onrender.com/img/users/${state.profile?.imageUrl}",
                        onChangeImage = {})
                    Spacer(modifier = Modifier.width(LocalDimens.current.extraLarge))
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "${state.profile?.firstname?.capitalize()} ${state.profile?.lastname?.capitalize()}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        state.profile?.email?.let {
                            Text(
                                text = state.profile.email,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        CLibButton(onClick = { /*TODO*/ }) {
                            Text(
                                text = "Edit", style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(LocalDimens.current.extraLarge))
                Card(
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                ) {
                    ProfileListItem(
                        leading = Icons.Default.MenuBook,
                        onClick = onOpenMyBooks,
                        title = stringResource(R.string.my_books),
                    )
                    Divider(
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                    )
                    ProfileListItem(
                        leading = Icons.Default.FavoriteBorder,
                        onClick = {},
                        title = stringResource(R.string.favourites)
                    )
                    Divider(
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                    )
                    ProfileListItem(
                        leading = Icons.Default.WbSunny,
                        onClick = {},
                        title = stringResource(R.string.display)
                    )
                    Divider(
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                    )
                    ProfileListItem(
                        leading = Icons.Default.NotificationsNone,
                        onClick = {},
                        title = stringResource(R.string.notifications)
                    )
                }
                Spacer(modifier = Modifier.height(LocalDimens.current.extraLarge))
                Card(
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                ) {
                    ProfileListItem(leading = Icons.Default.DeleteOutline,
                        title = stringResource(R.string.clear_cache),
                        onClick = { onToggleCacheDialog(true) })
                    Divider(
                        thickness = 0.4.dp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge)
                    )
                    ProfileListItem(
                        leading = Icons.Default.Logout,
                        title = stringResource(R.string.logout),
                        onClick = onSignOut
                    )
                }
            }
            Text(
                text = "Version $versionName",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = LocalDimens.current.extraLarge),
                textAlign = TextAlign.Center
            )
        }
    }
}