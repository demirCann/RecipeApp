package com.demircandemir.reciper.navigation

import android.app.Activity.RESULT_OK
import android.util.Log
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.demircandemir.reciper.presentation.detail.DetailScreen
import com.demircandemir.reciper.presentation.diet.DietScreen
import com.demircandemir.reciper.presentation.favorites.FavoritesScreen
import com.demircandemir.reciper.presentation.meal_type.MealTypeListScreen
import com.demircandemir.reciper.presentation.meals.MealsScreen
import com.demircandemir.reciper.presentation.register.RegisterScreen
import com.demircandemir.reciper.presentation.search.SearchScreen
import com.demircandemir.reciper.presentation.sign_in.GoogleAuthUiClient
import com.demircandemir.reciper.presentation.sign_in.mail.MailSignInScreenScreen
import com.demircandemir.reciper.presentation.sign_in.SignInScreen
import com.demircandemir.reciper.presentation.sign_in.SignInViewModel
import com.demircandemir.reciper.presentation.splash.SplashScreen
import com.demircandemir.reciper.util.Constants.DIET_ARGUMENT_KEY
import com.demircandemir.reciper.util.Constants.MEAL_TYPE_ARGUMENT_KEY
import com.demircandemir.reciper.util.Constants.RECIPE_DETAIL_ID_KEY
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecipeNavigationGraph(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = RecipeNavigation.HOME,
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




    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(route = RecipeNavigation.SPLASH) {
            SplashScreen(
                onNavigateToSignIn = navActions.navigateToAuth
            )
        }


        navigation(
            startDestination = RecipeNavigation.LOGIN,
            route = RecipeNavigation.AUTH
        ) {
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

            composable(route = RecipeNavigation.MAIL_SIGN_IN) {
                MailSignInScreenScreen(
                    onSignInClick = {
                        navController.navigate(RecipeNavigation.HOME) {
                            popUpTo(RecipeNavigation.AUTH) {
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
                            popUpTo(RecipeNavigation.AUTH) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }


        navigation(
            startDestination = RecipeNavigation.FAVORITES,
            route = RecipeNavigation.HOME
        ) {
            composable(route = RecipeNavigation.RECIPE_LIST) {
                MealsScreen(
                    onAllMealsForTypes = {
                        navActions.navigateToMealTypeList(it)
                    },
                    onNavigateDetail = {
                        Log.d("RecipeNavigationGraph", "onNavigateDetail: $it")
//                        navController.navigate("recipe_detail/$it")
                        navActions.navigateToRecipeDetail(it)
                    },
                    onNavigateDietScreen = {
                        navActions.navigateToDietScreen(it)
                    },
                    navController = navController,
                    onSearchClicked = {
                        navActions.navigateToSearch()
                    }
                )
            }

            composable(
                route = RecipeNavigation.MEAL_TYPE_LIST,
                arguments = listOf(navArgument(MEAL_TYPE_ARGUMENT_KEY) {
                    type = NavType.StringType
                })
            ) {
                MealTypeListScreen(
                    onSearchClicked = { navActions.navigateToSearch() },
                    onBackClicked = { navController.popBackStack() },
                    onNavigateDetail = {
                        navActions.navigateToRecipeDetail(it)
                    }
                )
            }

            composable(
                route = RecipeNavigation.DIET,
                arguments = listOf(navArgument(DIET_ARGUMENT_KEY) {
                    type = NavType.StringType
                })
            ) {
                DietScreen(
                    onSearchClicked = { navActions.navigateToSearch() },
                    onBackClicked = { navController.popBackStack() },
                    onNavigateDetail = {
                        navActions.navigateToRecipeDetail(it)
                    }
                )
            }


            composable(route = RecipeNavigation.FAVORITES) {
                 FavoritesScreen(
                     navController,
                     onNavigateDetail = {
                         navActions.navigateToRecipeDetail(it)
                     }
                 )
            }

            composable(
                route = RecipeNavigation.RECIPE_DETAIL,
                arguments = listOf(navArgument(RECIPE_DETAIL_ID_KEY) {
                    type = NavType.IntType
                })
            ) {

                DetailScreen(
                    onFinishedClicked = {
                        navController.popBackStack()
                    }
                )

            }

            composable(
                route = RecipeNavigation.SEARCH
            ) {
                SearchScreen(
                    onClosedClicked = { navController.popBackStack() },
                    navigateToDetail = { navActions.navigateToRecipeDetail(it) }
                )
            }


        }



//        composable(route = RecipeNavigation.HOME) {
//            HomeScreen(
//                onLogoutClicked = {
//                    navController.navigate(RecipeNavigation.LOGIN) {
//                        popUpTo(RecipeNavigation.HOME) {
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }



    }
}