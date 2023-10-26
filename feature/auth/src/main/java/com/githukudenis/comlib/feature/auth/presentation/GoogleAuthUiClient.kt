package com.githukudenis.comlib.feature.auth.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.githukudenis.comlib.feature.auth.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
) {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    suspend fun signInWithIntent(intent: Intent): SignInResult? {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = firebaseAuth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                userData = user?.run {
                    UserData(
                        id = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString(),
                        email = email
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(userData = null, errorMessage = e.message)
        }
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            firebaseAuth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = firebaseAuth.currentUser?.run {
        UserData(
            id = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString(),
            email = email
        )
    }

    suspend fun signIn(): IntentSender? {
        return try {
            oneTapClient.beginSignIn(buildBeginSignInRequest()).await().pendingIntent.intentSender
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
    }

    private fun buildBeginSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}