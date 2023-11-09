package com.githukudenis.comlib.feature.auth.presentation.signup

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.text_fields.CLibOutlinedTextField
import com.githukudenis.comlib.feature.auth.R
import com.githukudenis.comlib.feature.auth.presentation.GoogleAuthUiClient
import com.githukudenis.comlib.feature.auth.presentation.login.LoginUiEvent
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun SignUpRoute(
    viewModel: SignUpViewModel = hiltViewModel(), onSignUpComplete: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val signUpComplete by rememberUpdatedState(onSignUpComplete)

    LaunchedEffect(state.signUpSuccess) {
        if (state.signUpSuccess) {
            signUpComplete()
        }
    }

    val googleAuthUiClient: GoogleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context),
        )
    }

    val signInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    scope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            activityResult.data ?: return@launch
                        )
                        viewModel.onEvent(SignUpUiEvent.GoogleSignIn(signInResult ?: return@launch))
                    }
                }
            })

    SignUpScreen(state = state,
        context = context,
        onChangeFirstname = { firstname -> viewModel.onEvent(SignUpUiEvent.ChangeFirstname(firstname)) },
        onChangeLastname = { lastname -> viewModel.onEvent(SignUpUiEvent.ChangeLastname(lastname)) },
        onChangeEmail = { email -> viewModel.onEvent(SignUpUiEvent.ChangeEmail(email)) },
        onChangeAge = { age -> viewModel.onEvent(SignUpUiEvent.ChangeAge(age)) },
        onChangePassword = { password -> viewModel.onEvent(SignUpUiEvent.ChangePassword(password)) },
        onTogglePasswordVisibility = { visible ->
            viewModel.onEvent(
                SignUpUiEvent.TogglePasswordVisibility(
                    visible
                )
            )
        },
        onChangeConfirmPassword = { confirm ->
            viewModel.onEvent(
                SignUpUiEvent.ChangeConfirmPassword(
                    confirm
                )
            )
        },
        onToggleConfirmPasswordVisibility = { visible ->
            viewModel.onEvent(
                SignUpUiEvent.ToggleConfirmPasswordVisibility(
                    visible
                )
            )
        },
        onGoogleSignIn = {
            scope.launch {
                val intentSender = googleAuthUiClient.signIn()
                val intent = IntentSenderRequest.Builder(intentSender ?: return@launch).build()

                signInLauncher.launch(
                    intent
                )
            }
        },
        onDismissUserMessage = { id -> viewModel.onEvent(SignUpUiEvent.DismissUserMessage(id)) },
        onSubmit = {
            viewModel.onEvent(SignUpUiEvent.Submit)
        })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SignUpScreen(
    state: SignUpUiState,
    context: Context,
    onChangeFirstname: (String) -> Unit,
    onChangeLastname: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangeAge: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onTogglePasswordVisibility: (Boolean) -> Unit,
    onChangeConfirmPassword: (String) -> Unit,
    onToggleConfirmPasswordVisibility: (Boolean) -> Unit,
    onDismissUserMessage: (Int) -> Unit,
    onGoogleSignIn: () -> Unit,
    onSubmit: () -> Unit,
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .imeNestedScroll()
                .padding(
                    PaddingValues(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                text = stringResource(id = R.string.signup_header_title),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.signup_header_description),
                style = MaterialTheme.typography.labelMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            CLibOutlinedTextField(
                value = state.formState.firstname, onValueChange = onChangeFirstname, label = {
                    Text(
                        text = stringResource(id = R.string.firstname_hint)
                    )
                }, keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ), modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            CLibOutlinedTextField(
                value = state.formState.lastname,
                onValueChange = onChangeLastname,
                label = {
                    Text(
                        text = stringResource(id = R.string.lastname_hint)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            CLibOutlinedTextField(
                value = state.formState.email,
                onValueChange = onChangeEmail,
                label = {
                    Text(
                        text = stringResource(id = R.string.email_hint)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            CLibOutlinedTextField(
                value = state.formState.age, onValueChange = onChangeAge, label = {
                    Text(
                        text = stringResource(id = R.string.age_hint)
                    )
                }, keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ), modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            CLibOutlinedTextField(
                value = state.formState.password,
                onValueChange = onChangePassword,
                label = {
                    Text(
                        text = stringResource(id = R.string.password_hint)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                ),
                trailingIcon = {
                    IconButton(onClick = { onTogglePasswordVisibility(!state.formState.passwordIsVisible) }) {
                        Icon(
                            imageVector = if (state.formState.passwordIsVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = context.getString(R.string.toggle_password_visibility_txt)
                        )
                    }
                },
                visualTransformation = if (state.formState.passwordIsVisible) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            CLibOutlinedTextField(
                value = state.formState.confirmPassword,
                onValueChange = onChangeConfirmPassword,
                label = {
                    Text(
                        text = stringResource(id = R.string.confirm_password_hint)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Go
                ),
                trailingIcon = {
                    IconButton(onClick = { onToggleConfirmPasswordVisibility(!state.formState.confirmPasswordIsVisible) }) {
                        Icon(
                            imageVector = if (state.formState.confirmPasswordIsVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = context.getString(R.string.toggle_password_visibility_txt)
                        )
                    }
                },
                visualTransformation = if (state.formState.confirmPasswordIsVisible) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

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
            Spacer(modifier = Modifier.height(8.dp))
            CLibOutlinedButton(
                onClick = onGoogleSignIn,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.login_footer_separator)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Sign up with google"
                )
            }
        }
    }
}
