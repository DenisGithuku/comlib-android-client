
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
package com.githukudenis.comlib.feature.auth.presentation.login

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibLoadingDialog
import com.githukudenis.comlib.core.designsystem.ui.components.text_fields.CLibOutlinedTextField
import com.githukudenis.comlib.core.designsystem.ui.theme.Critical
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.auth.R
import com.githukudenis.comlib.feature.auth.presentation.GoogleAuthUiClient
import com.githukudenis.comlib.feature.auth.presentation.common.AuthProviderButton
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onForgotPassword: () -> Unit,
    onSignUpInstead: () -> Unit,
    onLoginComplete: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val googleAuthUiClient: GoogleAuthUiClient by lazy {
        GoogleAuthUiClient(context = context, oneTapClient = Identity.getSignInClient(context))
    }

    val loginOnComplete by rememberUpdatedState(newValue = onLoginComplete)

    LaunchedEffect(key1 = state.loginSuccess) {
        if (state.loginSuccess) {
            Toast.makeText(context, context.getString(R.string.login_success_txt), Toast.LENGTH_SHORT)
                .show()
            delay(2_000)
            loginOnComplete()
        }
    }

    val signInLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
                    scope.launch {
                        val signInResult =
                            googleAuthUiClient.signInWithIntent(activityResult.data ?: return@launch)
                        viewModel.onEvent(LoginUiEvent.GoogleSignIn(signInResult ?: return@launch))
                    }
                }
            }
        )

    LoginScreen(
        state = state,
        context = context,
        onEmailChange = { email -> viewModel.onEvent(LoginUiEvent.ChangeEmail(email)) },
        onPasswordChange = { password -> viewModel.onEvent(LoginUiEvent.ChangePassword(password)) },
        onForgotPassword = onForgotPassword,
        onSignUpInstead = onSignUpInstead,
        onGoogleSignIn = {
            scope.launch {
                val intentSender = googleAuthUiClient.signIn()
                val intent = IntentSenderRequest.Builder(intentSender ?: return@launch).build()

                signInLauncher.launch(intent)
            }
        },
        onTogglePasswordVisibility = { isVisible ->
            viewModel.onEvent(LoginUiEvent.TogglePassword(isVisible))
        },
        onDismissUserMessage = { id -> viewModel.onEvent(LoginUiEvent.DismissUserMessage(id)) },
        onToggleRememberMe = { viewModel.onEvent(LoginUiEvent.ToggleRememberMe(it)) },
        onSubmit = { viewModel.onEvent(LoginUiEvent.SubmitData) }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LoginScreen(
    state: LoginUiState,
    context: Context,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUpInstead: () -> Unit,
    onGoogleSignIn: () -> Unit,
    onTogglePasswordVisibility: (Boolean) -> Unit,
    onDismissUserMessage: (Int) -> Unit,
    onSubmit: () -> Unit,
    onToggleRememberMe: ((Boolean) -> Unit)?
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(
            modifier =
                Modifier.fillMaxSize()
                    .safeDrawingPadding()
                    .imePadding()
                    .imeNestedScroll()
                    .padding(
                        PaddingValues(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                    )
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CLibLoadingDialog(label = context.getString(R.string.logging_in_txt))
            }

            LaunchedEffect(snackbarHostState, state.userMessages) {
                if (state.userMessages.isNotEmpty()) {
                    val userMessage = state.userMessages.first()
                    snackbarHostState.showSnackbar(
                        message = userMessage.message ?: return@LaunchedEffect,
                        duration = SnackbarDuration.Short
                    )
                    onDismissUserMessage(userMessage.id)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.login_header_description),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            CLibOutlinedTextField(
                value = state.formState.email,
                onValueChange = onEmailChange,
                label = stringResource(id = R.string.email_hint),
                modifier = Modifier.fillMaxWidth().onFocusChanged { isEmailFocused = it.isFocused },
                supportingText = {
                    if (isEmailFocused && !state.formState.isEmailValid) {
                        Text(
                            text = stringResource(id = R.string.invalid_email),
                            style = MaterialTheme.typography.bodySmall,
                            color = Critical
                        )
                    }
                },
                isError = !state.formState.isEmailValid && isEmailFocused
            )
            Spacer(modifier = Modifier.height(8.dp))

            CLibOutlinedTextField(
                value = state.formState.password,
                onValueChange = onPasswordChange,
                label = stringResource(id = R.string.password_hint),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { onTogglePasswordVisibility(!state.formState.passwordIsVisible) }) {
                        Icon(
                            imageVector =
                                if (state.formState.passwordIsVisible) {
                                    Icons.Default.Visibility
                                } else Icons.Default.VisibilityOff,
                            contentDescription = context.getString(R.string.toggle_password_visibility_txt)
                        )
                    }
                },
                visualTransformation =
                    if (state.formState.passwordIsVisible) {
                        PasswordVisualTransformation()
                    } else VisualTransformation.None,
                modifier = Modifier.fillMaxWidth().onFocusChanged { isPasswordFocused = it.isFocused },
                supportingText = {
                    if (
                        isPasswordFocused &&
                            !state.formState.requirements.containsAll(PasswordRequirements.entries.toList())
                    ) {
                        val missingRequirement =
                            PasswordRequirements.entries.first { it !in state.formState.requirements }
                        Text(
                            text = stringResource(id = missingRequirement.label),
                            style = MaterialTheme.typography.bodySmall,
                            color = Critical
                        )
                    }
                },
                isError = state.formState.requirements.size < 4 && isPasswordFocused
            )
            Spacer(modifier = Modifier.height(8.dp))

            CLibButton(
                onClick = onSubmit,
                enabled = state.formState.formIsValid,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.login_button_txt),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.small)
                ) {
                    Checkbox(checked = state.formState.rememberMe, onCheckedChange = onToggleRememberMe)
                    Text(
                        text = stringResource(id = R.string.remember_me),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                CLibTextButton(onClick = onForgotPassword) {
                    Text(
                        text = stringResource(id = R.string.forgot_pass_btn_txt),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            AuthProviderButton(icon = R.drawable.ic_google, onClick = onGoogleSignIn)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_account_txt),
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.width(LocalDimens.current.medium))
                CLibTextButton(onClick = onSignUpInstead) {
                    Text(
                        text = stringResource(id = R.string.sign_up_txt),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        state = LoginUiState(isLoading = true),
        context = LocalContext.current,
        onEmailChange = {},
        onPasswordChange = {},
        onForgotPassword = { /*TODO*/},
        onSignUpInstead = { /*TODO*/},
        onGoogleSignIn = { /*TODO*/},
        onTogglePasswordVisibility = {},
        onDismissUserMessage = {},
        onSubmit = {},
        onToggleRememberMe = {}
    )
}
