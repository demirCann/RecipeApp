package com.demircandemir.reciper.presentation.meals

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.demircandemir.reciper.R
import com.demircandemir.reciper.data.source.network.response.Result

@Composable
fun FoodCard(
    meal: Result,
    isFavorite: Boolean = false,
    onAddedFavorite: (Result) -> Unit,
    navigateToDetail: (Int) -> Unit
) {

    var favoriteState by remember { mutableStateOf(isFavorite) }

    Card(
        onClick = {
            navigateToDetail(meal.id)
        },
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .size(width = 150.dp, height = 250.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = meal.image,
                contentDescription = "Default meal image",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Box(
                contentAlignment = Alignment.BottomStart,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = meal.title,
                        modifier = Modifier
                            .padding(top = 8.dp),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                if (!favoriteState) {
                                    favoriteState = true
                                    onAddedFavorite(meal)
                                    Log.d("FoodCard", "Added to favorite")
                                } else {
                                    favoriteState = false
                                    onAddedFavorite(meal)
                                    Log.d("FoodCard", "Removed from favorite")
                                    onAddedFavorite(meal)
                                }

                            }
                        ) {
                            Icon(
                                imageVector = if (favoriteState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(R.string.favorite)
                            )
                        }
                    }
                }
            }

        }
    }
}