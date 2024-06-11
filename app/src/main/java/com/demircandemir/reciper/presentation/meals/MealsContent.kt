package com.demircandemir.reciper.presentation.meals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MealsContent(
    breakfastRecipes: MealState,
    lunchRecipes: MealState,
    dessertRecipes: MealState,
    snackRecipes: MealState,
    soupRecipes: MealState,
    drinkRecipes: MealState,
    onAllMealsForTypes: (String) -> Unit,
    onNavigateDetail: (Int) -> Unit
) {

    Column {
        MealSection("Breakfast", breakfastRecipes, onAllMealsForTypes, onNavigateDetail)
        MealSection("Main Course", lunchRecipes, onAllMealsForTypes, onNavigateDetail)
        MealSection("Dessert", dessertRecipes, onAllMealsForTypes, onNavigateDetail)
        MealSection("Snack", snackRecipes, onAllMealsForTypes, onNavigateDetail)
        MealSection("Soup", soupRecipes, onAllMealsForTypes, onNavigateDetail)
        MealSection("Drink", drinkRecipes, onAllMealsForTypes, onNavigateDetail)
    }

}


@Composable
fun MealSection(
    mealType: String,
    mealState: MealState,
    onAllMealsForTypes: (String) -> Unit,
    onNavigateDetail: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = mealType, style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = { onAllMealsForTypes(mealType) }) {
                Text("See all")
            }
        }

        when (mealState) {
            is MealState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is MealState.Success -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(mealState.meals.number) { index ->
                        FoodCard(
                            mealState.meals.results[index],
                            navigateToDetail = { mealId -> onNavigateDetail(mealId)}
                        )
                    }
                }
            }
            is MealState.Error -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = mealState.message)
                }
            }
        }
    }
}