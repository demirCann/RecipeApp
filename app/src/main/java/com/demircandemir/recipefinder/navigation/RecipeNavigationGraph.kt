package com.demircandemir.recipefinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demircandemir.recipefinder.presentation.home.HomeScreen
import com.demircandemir.recipefinder.presentation.splash.SplashScreen
import kotlinx.coroutines.CoroutineScope

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
    val currentNavigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavigationBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = RecipeNavigation.SPLASH) {
            SplashScreen(navController)
        }

        composable(route = RecipeNavigation.HOME) {
            HomeScreen()
        }

    }
}