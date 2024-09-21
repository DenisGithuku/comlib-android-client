
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
package com.githukudenis.comlib.feature.auth.presentation.signup

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibLoadingDialog
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibMinimalDialog
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
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(),
    onSignUpComplete: () -> Unit,
    onSignInInstead: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val showNetworkDialog by viewModel.showNetworkDialog.collectAsStateWithLifecycle()
    val signUpComplete by rememberUpdatedState(onSignUpComplete)

    LaunchedEffect(state.signUpSuccess) {
        if (state.signUpSuccess) {
            Toast.makeText(context, context.getString(R.string.sign_up_success_txt), Toast.LENGTH_SHORT)
                .show()
            delay(2_000)
            signUpComplete()
        }
    }

    if (showNetworkDialog) {
        CLibMinimalDialog(
            title = stringResource(id = R.string.no_network_title),
            text = stringResource(id = R.string.no_network_desc),
            onDismissRequest = { viewModel.onDismissNetworkDialog() }
        )
        return
    }

    val googleAuthUiClient: GoogleAuthUiClient by lazy {
        GoogleAuthUiClient(context = context, oneTapClient = Identity.getSignInClient(context))
    }

    val signInLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    scope.launch {
                        val signInResult =
                            googleAuthUiClient.signInWithIntent(activityResult.data ?: return@launch)
                        viewModel.onEvent(SignUpUiEvent.GoogleSignIn(signInResult ?: return@launch))
                    }
                }
            }
        )

    SignUpScreen(
        state = state,
        context = context,
        onChangeFirstname = { firstname ->
            viewModel.onEvent(SignUpUiEvent.ChangeFirstname(firstname))
        },
        onChangeLastname = { lastname -> viewModel.onEvent(SignUpUiEvent.ChangeLastname(lastname)) },
        onChangeEmail = { email -> viewModel.onEvent(SignUpUiEvent.ChangeEmail(email)) },
        onChangePassword = { password -> viewModel.onEvent(SignUpUiEvent.ChangePassword(password)) },
        onTogglePasswordVisibility = { visible ->
            viewModel.onEvent(SignUpUiEvent.TogglePasswordVisibility(visible))
        },
        onChangeConfirmPassword = { confirm ->
            viewModel.onEvent(SignUpUiEvent.ChangeConfirmPassword(confirm))
        },
        onToggleConfirmPasswordVisibility = { visible ->
            viewModel.onEvent(SignUpUiEvent.ToggleConfirmPasswordVisibility(visible))
        },
        onGoogleSignIn = {
            scope.launch {
                val intentSender = googleAuthUiClient.signIn()
                val intent = IntentSenderRequest.Builder(intentSender ?: return@launch).build()

                signInLauncher.launch(intent)
            }
        },
        onDismissUserMessage = { id -> viewModel.onEvent(SignUpUiEvent.DismissUserMessage(id)) },
        onSubmit = { viewModel.onEvent(SignUpUiEvent.Submit) },
        onSignInInstead = onSignInInstead,
        onToggleTerms = { viewModel.onEvent(SignUpUiEvent.ToggleTerms(it)) }
    )
}

@Composable
private fun SignUpScreen(
    state: SignUpUiState,
    context: Context,
    onChangeFirstname: (String) -> Unit,
    onChangeLastname: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onTogglePasswordVisibility: (Boolean) -> Unit,
    onChangeConfirmPassword: (String) -> Unit,
    onToggleConfirmPasswordVisibility: (Boolean) -> Unit,
    onToggleTerms: (Boolean) -> Unit,
    onDismissUserMessage: (Int) -> Unit,
    onSignInInstead: () -> Unit,
    onGoogleSignIn: () -> Unit,
    onSubmit: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isConfirmFocused by remember { mutableStateOf(false) }

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

    if (state.isLoading) {
        CLibLoadingDialog(label = context.getString(R.string.signing_up_txt))
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier.fillMaxSize()
                    .padding(
                        PaddingValues(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        )
                    ),
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.signup_header_title),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium)
                ) {
                    CLibOutlinedTextField(
                        value = state.formState.firstname,
                        onValueChange = onChangeFirstname,
                        label = stringResource(id = R.string.firstname_hint),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier.weight(1f)
                    )

                    CLibOutlinedTextField(
                        value = state.formState.lastname,
                        onValueChange = onChangeLastname,
                        label = stringResource(id = R.string.lastname_hint),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                CLibOutlinedTextField(
                    value = state.formState.email,
                    onValueChange = onChangeEmail,
                    label = stringResource(id = R.string.email_hint),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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
            }
            item {
                CLibOutlinedTextField(
                    value = state.formState.password,
                    onValueChange = onChangePassword,
                    label = stringResource(id = R.string.password_hint),
                    keyboardOptions =
                        KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                    trailingIcon = {
                        IconButton(
                            onClick = { onTogglePasswordVisibility(!state.formState.passwordIsVisible) }
                        ) {
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
                        if (isPasswordFocused && state.formState.requirements.size < 4) {
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
            }
            item {
                CLibOutlinedTextField(
                    value = state.formState.confirmPassword,
                    onValueChange = onChangeConfirmPassword,
                    label = stringResource(id = R.string.confirm_password_hint),
                    keyboardOptions =
                        KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Go),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onToggleConfirmPasswordVisibility(!state.formState.confirmPasswordIsVisible)
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (state.formState.confirmPasswordIsVisible) {
                                        Icons.Default.Visibility
                                    } else Icons.Default.VisibilityOff,
                                contentDescription = context.getString(R.string.toggle_password_visibility_txt)
                            )
                        }
                    },
                    visualTransformation =
                        if (state.formState.confirmPasswordIsVisible) {
                            PasswordVisualTransformation()
                        } else VisualTransformation.None,
                    modifier = Modifier.fillMaxWidth().onFocusChanged { isConfirmFocused = it.isFocused },
                    supportingText = {
                        if (isConfirmFocused && state.formState.password != state.formState.confirmPassword) {
                            Text(
                                text = stringResource(id = R.string.password_mismatch),
                                style = MaterialTheme.typography.bodySmall,
                                color = Critical
                            )
                        }
                    },
                    isError = state.formState.password != state.formState.confirmPassword && isConfirmFocused
                )
            }
            item {
                CLibButton(
                    onClick = onSubmit,
                    enabled = state.formState.formIsValid,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_button_txt),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.small)
                ) {
                    Checkbox(checked = state.formState.acceptedTerms, onCheckedChange = onToggleTerms)
                    Text(
                        text = stringResource(R.string.terms_label),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            item { AuthProviderButton(onClick = onGoogleSignIn, icon = R.drawable.ic_google) }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.has_account),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(modifier = Modifier.width(LocalDimens.current.medium))
                    CLibTextButton(onClick = onSignInInstead) {
                        Text(
                            text = stringResource(id = R.string.sign_in_txt),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}
