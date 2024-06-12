package com.demircandemir.reciper.presentation.favorites

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.demircandemir.reciper.R
import com.demircandemir.reciper.presentation.common.BottomNavigationBar
import com.demircandemir.reciper.presentation.common.ListContent
import com.demircandemir.reciper.presentation.meals.MealsTopBar

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoritesViewModel = hiltViewModel(),
    onNavigateDetail: (Int) -> Unit
) {

    val favoriteState by viewModel.favoritesState.collectAsState()

    Scaffold(
        topBar = {
            MealsTopBar(
                topBarName = stringResource(R.string.favorites),
                onSearchClicked = {},
                onBackClicked = {}
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(it)
            ) {

                when (favoriteState) {
                    is FavoriteState.Loading -> {
                        // Loading
                        Log.d("MealsScreen", "MealState.Loading")
                    }

                    is FavoriteState.Success -> {
                        val meals = (favoriteState as FavoriteState.Success).favorites
                        Log.d("MealsScreen", "MealState.Success: ${meals.size}")
                        ListContent(
                            meals = meals,
                            isFavorite = true,
                            navigateToDetail = onNavigateDetail,
                            onAddedFavorite = { meal ->
                                viewModel.removeMealFromFavorites(meal.id)
                            }
                        )
                    }

                    is FavoriteState.Error -> {
                        val message = (favoriteState as FavoriteState.Error).message
                        Log.d("MealsScreen", "MealState.Error - $message")
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                }


            }


        }
    )

}