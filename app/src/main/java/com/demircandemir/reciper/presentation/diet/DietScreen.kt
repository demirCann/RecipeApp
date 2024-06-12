package com.demircandemir.reciper.presentation.diet

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.demircandemir.reciper.presentation.common.ListContent
import com.demircandemir.reciper.presentation.meals.MealsTopBar

@Composable
fun DietScreen(
    viewModel: DietViewModel = hiltViewModel(),
    onSearchClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onNavigateDetail: (Int) -> Unit
) {

    val mealState by viewModel.mealState.collectAsState()

    val diet = viewModel.diet


    Scaffold(
        topBar = {
            MealsTopBar(
                topBarName = diet!!.replaceFirst(diet[0], diet[0].uppercaseChar()),
                onSearchClicked = { onSearchClicked() },
                onBackClicked = { onBackClicked() }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(it)
            ) {

                when (mealState) {
                    is MealState.Loading -> {
                        // Loading
                        Log.d("MealsScreen", "MealState.Loading")

                    }

                    is MealState.Success -> {
                        Log.d("MealsScreen", "MealState.Success")
                        val meals = (mealState as MealState.Success).meals.results
                        ListContent(
                            meals = meals,
                            navigateToDetail = onNavigateDetail,
                            onAddedFavorite = { meal ->
                                viewModel.addFavorite(meal)
                            }
                        )
                    }

                    is MealState.Error -> {
                        val message = (mealState as MealState.Error).message
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