package com.demircandemir.reciper.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    onFinishedClicked: () -> Unit
) {
    val mealState by detailViewModel.mealState.collectAsState()


    when(mealState) {
        is MealState.Loading -> {
            // Loading
        }
        is MealState.Success -> {
            val meal = (mealState as MealState.Success).meals
            DetailsContent(
                meal = meal,
                onFinishedClicked = onFinishedClicked
            )
        }
        is MealState.Error -> {
            val message = (mealState as MealState.Error).message
        }
    }


}