package com.demircandemir.reciper.navigation

import androidx.compose.material.icons.Icons
import androidx.navigation.NavHostController

object RecipeNavigation {
    const val SPLASH = "splash"
    const val AUTH = "auth"
    const val LOGIN = "login"
    const val MAIL_SIGN_IN = "mail_sign_in"
    const val REGISTER = "register"
    const val SURVEY = "survey"
    const val HOME = "home"
    const val RECIPE_LIST = "recipe"
    const val MEAL_TYPE_LIST = "meal_type_list/{mealType}"
    const val DIET = "diet/{diet}"
    const val FAVORITES = "favorites"
    const val SEARCH = "search"
    const val RECIPE_DETAIL = "recipe_detail/{recipeId}"
}


class RecipeNavigationActions(private val navController: NavHostController) {
    val navigateToSplash: () -> Unit = {
        navController.navigate(RecipeNavigation.SPLASH)
    }

    val navigateToAuth: () -> Unit = {
        navController.navigate(RecipeNavigation.AUTH)
    }

    val navigateToLogin: () -> Unit = {
        navController.navigate(RecipeNavigation.LOGIN)
    }
    val navigateToMailSignIn: () -> Unit = {
        navController.navigate(RecipeNavigation.MAIL_SIGN_IN)
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

    val navigateToMealTypeList: (mealType: String) -> Unit = { mealType ->
        navController.navigate("meal_type_list/$mealType")
    }

    val navigateToDietScreen: (diet: String) -> Unit = { diet ->
        navController.navigate("diet/$diet")
    }

    val navigateToFavorites: () -> Unit = {
        navController.navigate(RecipeNavigation.FAVORITES)
    }

    val navigateToSearch: () -> Unit = {
        navController.navigate(RecipeNavigation.SEARCH)
    }
    val navigateToRecipeDetail: (recipeId: Int) -> Unit = { recipeId ->
        navController.navigate("recipe_detail/$recipeId")
    }
}