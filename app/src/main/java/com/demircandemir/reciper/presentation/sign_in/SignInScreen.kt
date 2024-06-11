package com.demircandemir.reciper.presentation.sign_in

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun SignInScreen(
    state: SignInState,
    onSignInClick: () -> Unit,
    onEmailClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {


    val context = LocalContext.current


    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    SignInScreenContent(
        onGoogleSignInClick = {
            onSignInClick()
        },
        onEmailClicked = {
            onEmailClicked()
        },
        onRegisterClicked = {
            onRegisterClicked()
        }
    )


}


