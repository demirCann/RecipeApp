package com.demircandemir.reciper.presentation.meals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.demircandemir.reciper.presentation.common.BottomNavigationBar

@Composable
fun MealsScreen(
    onAllMealsForTypes: (String) -> Unit,
    navController: NavHostController,
    viewModel: MealsViewModel = hiltViewModel(),
    onNavigateDetail: (Int) -> Unit,
    onNavigateDietScreen: (String) -> Unit,
    onSearchClicked: () -> Unit
) {

    val breakfastRecipes by viewModel.breakfastRecipes.collectAsState()
    val lunchRecipes by viewModel.lunchRecipes.collectAsState()
    val dessertRecipes by viewModel.dessertRecipes.collectAsState()
    val snackRecipes by viewModel.snackRecipes.collectAsState()
    val soupRecipes by viewModel.soupRecipes.collectAsState()
    val drinkRecipes by viewModel.drinkRecipes.collectAsState()

    val scrollState = rememberScrollState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MealsTopBar(
                topBarName = "Meals",
                onSearchClicked = onSearchClicked,
                onBackClicked = {}
            )

        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
        ) {

            Carousel(
                onNavigateDietScreen = onNavigateDietScreen,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )

            MealsContent(
                breakfastRecipes = breakfastRecipes,
                lunchRecipes = lunchRecipes,
                dessertRecipes = dessertRecipes,
                snackRecipes = snackRecipes,
                soupRecipes = soupRecipes,
                drinkRecipes = drinkRecipes,
                onAllMealsForTypes = onAllMealsForTypes,
                onNavigateDetail = onNavigateDetail,
                onAddedFavorite = { meal ->
                    viewModel.addFavorite(meal)
                }
            )
        }




    }
}