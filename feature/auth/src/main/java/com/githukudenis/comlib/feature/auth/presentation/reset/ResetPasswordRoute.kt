package com.githukudenis.comlib.feature.auth.presentation.reset

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.text_fields.CLibOutlinedTextField
import com.githukudenis.comlib.feature.auth.R
import kotlinx.coroutines.delay

@Composable
fun ResetPasswordRoute(
    snackbarHostState: SnackbarHostState,
    viewModel: ResetPasswordViewModel = hiltViewModel(), onReset: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onPasswordReset by rememberUpdatedState(onReset)
    val context = LocalContext.current


    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            snackbarHostState.showSnackbar(
                message = context.getString(R.string.email_reset)
            )
            delay(2000)
            onPasswordReset()
        }
    }

    ResetPasswordScreen(state = state,
        onEmailChange = { viewModel.onEmailChange(it) },
        onReset = { viewModel.onReset() })
}

@Composable
fun ResetPasswordScreen(
    state: ResetUiState, onEmailChange: (String) -> Unit, onReset: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    top = PaddingValues().calculateTopPadding(),
                    bottom = PaddingValues().calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            CircularProgressIndicator()
        }
        Image(
            painter = painterResource(R.drawable.comliblogo),
            modifier = Modifier.size(80.dp),
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.reset_header_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.reset_header_description),
            style = MaterialTheme.typography.labelMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CLibOutlinedTextField(
            value = state.email, onValueChange = onEmailChange, modifier = Modifier.fillMaxWidth()
        )
        CLibButton(
            onClick = onReset, enabled = state.isEmailValid, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.reset_button_text),
            )
        }
    }
}