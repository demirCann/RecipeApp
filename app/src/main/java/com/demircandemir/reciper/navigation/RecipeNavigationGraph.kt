package com.demircandemir.reciper.navigation

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demircandemir.reciper.presentation.home.HomeScreen
import com.demircandemir.reciper.presentation.register.RegisterScreen
import com.demircandemir.reciper.presentation.sign_in.GoogleAuthUiClient
import com.demircandemir.reciper.presentation.sign_in.mail.MailSignInScreenScreen
import com.demircandemir.reciper.presentation.sign_in.SignInScreen
import com.demircandemir.reciper.presentation.sign_in.SignInViewModel
import com.demircandemir.reciper.presentation.splash.SplashScreen
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipeNavigationGraph(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = RecipeNavigation.SPLASH,
    navActions: RecipeNavigationActions = remember(navController) {
        RecipeNavigationActions(navController)
    }
) {

    val applicationContext = LocalContext.current.applicationContext

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }


    val currentNavigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavigationBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = RecipeNavigation.SPLASH) {
            SplashScreen(
                onNavigateToSignIn = navActions.navigateToLogin
            )
        }

        composable(route = RecipeNavigation.LOGIN) {
            val viewModel = viewModel<SignInViewModel>()
            val state by viewModel.state.collectAsState()

            LaunchedEffect(key1 = Unit) {
                if(googleAuthUiClient.getSignedInUser() != null) {
                    navActions.navigateToHome()
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if(state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navActions.navigateToHome()
                    viewModel.resetState()
                }
            }

            SignInScreen(
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                onEmailClicked = navActions.navigateToMailSignIn,
                onRegisterClicked = navActions.navigateToRegister
            )
        }

        composable(route = RecipeNavigation.MAILSIGNIN) {
            MailSignInScreenScreen(
                onSignInClick = {
                    navController.navigate(RecipeNavigation.HOME) {
                        popUpTo(RecipeNavigation.MAILSIGNIN) {
                            inclusive = true
                        }

                    }
                }
            )
        }
        composable(route = RecipeNavigation.REGISTER) {
            RegisterScreen(
                onRegisterClicked = {
                    navController.navigate(RecipeNavigation.HOME) {
                        popUpTo(RecipeNavigation.REGISTER) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(route = RecipeNavigation.HOME) {
            HomeScreen(
                onLogoutClicked = {
                    navController.navigate(RecipeNavigation.LOGIN) {
                        popUpTo(RecipeNavigation.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}