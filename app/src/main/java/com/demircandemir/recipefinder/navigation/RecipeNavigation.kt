package com.demircandemir.recipefinder.navigation

import androidx.navigation.NavHostController

object RecipeNavigation {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val SURVEY = "survey"
    const val HOME = "home"
    const val RECIPE_LIST = "recipe"
    const val SEARCH = "search"
    const val RECIPE_DETAIL = "recipe_detail"
}


class RecipeNavigationActions(private val navController: NavHostController) {
    val navigateToSplash: () -> Unit = {
        navController.navigate(RecipeNavigation.SPLASH)
    }
    val navigateToLogin: () -> Unit = {
        navController.navigate(RecipeNavigation.LOGIN)
    }
    val navigateToRegister: () -> Unit = {
        navController.navigate(RecipeNavigation.REGISTER)
    }
    val navigateToSurvey: () -> Unit = {
        navController.navigate(RecipeNavigation.SURVEY)
    }
    val navigateToHome: () -> Unit = {
        navController.navigate(RecipeNavigation.HOME)
    }
    val navigateToRecipeList: () -> Unit = {
        navController.navigate(RecipeNavigation.RECIPE_LIST)
    }
    val navigateToSearch: () -> Unit = {
        navController.navigate(RecipeNavigation.SEARCH)
    }
    val navigateToRecipeDetail: () -> Unit = {
        navController.navigate(RecipeNavigation.RECIPE_DETAIL)
    }
}