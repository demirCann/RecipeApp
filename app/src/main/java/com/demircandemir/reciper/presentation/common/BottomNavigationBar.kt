package com.demircandemir.reciper.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.demircandemir.reciper.R
import com.demircandemir.reciper.navigation.RecipeNavigation
import com.demircandemir.reciper.presentation.home.HomeViewModel

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItem.List,
        BottomNavItem.Favorites,
        BottomNavItem.Logout
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        items.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }


}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    currentDestination: String?,
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    NavigationBarItem(
        label = {
            Text(
                text = screen.title
            )
        },
        icon = {
            Icon(imageVector = screen.icon, contentDescription = stringResource(R.string.bottombar_icon))
        },
        selected = currentDestination == screen.route,
        onClick = {

            if (screen is BottomNavItem.Logout) {
                homeViewModel.signOut()
                navController.navigate(RecipeNavigation.AUTH)
            } else {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }


        },
    )
}


sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    data object List : BottomNavItem("Recipes", Icons.Filled.List, RecipeNavigation.RECIPE_LIST)
    data object Favorites : BottomNavItem("Favorites", Icons.Filled.Favorite, RecipeNavigation.FAVORITES)

    data object Logout : BottomNavItem("Logout", Icons.AutoMirrored.Filled.ExitToApp, "logout")

}