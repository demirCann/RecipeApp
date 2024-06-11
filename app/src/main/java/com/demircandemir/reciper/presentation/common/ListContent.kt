package com.demircandemir.reciper.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.presentation.meals.FoodCard

@Composable
fun ListContent(
    meals: List<Result>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {


    LazyVerticalGrid(
        columns = GridCells.Adaptive(140.dp),
        modifier = modifier
            .fillMaxHeight(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = meals.size) {
            FoodCard(
                meal = meals[it],
                navigateToDetail = navigateToDetail
            )
        }
    }


}
