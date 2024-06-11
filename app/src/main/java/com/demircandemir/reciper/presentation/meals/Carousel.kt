package com.demircandemir.reciper.presentation.meals

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.demircandemir.reciper.R

@Composable
fun Carousel(
    modifier: Modifier = Modifier,
    onNavigateDietScreen: (String) -> Unit
) {


    val carouselItems = listOf(
        CarouselItemData(
            mealName = "Vegetarian",
            mealImageUri = R.drawable.vegetarian,
            diet = "vegetarian"
        ),
        CarouselItemData(
            mealName = "Vegan",
            mealImageUri = R.drawable.vegan,
            diet = "vegan"
        ),
        CarouselItemData(
            mealName = "Gluten Free",
            mealImageUri = R.drawable.gluten_free,
            diet = "gluten free"
        ),
        CarouselItemData(
            mealName = "Ketogenic",
            mealImageUri =R.drawable.ketogenic,
            diet = "ketogenic"
        ),
    )


    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(carouselItems, key = { it.mealName }) {
            CarouselItem(
                mealData = it,
                onClickedItem = {
                    onNavigateDietScreen(it.diet)
                }
            )
        }

    }


}



@Composable
fun CarouselItem(
    mealData: CarouselItemData,
    onClickedItem: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(width = 120.dp, 160.dp)
            .clip(shape = RoundedCornerShape(18.dp))
            .background(Color.DarkGray)
            .clickable {
                Log.d("CarouselItem", "onClickedItem: ")
                onClickedItem()
            },
        contentAlignment = Alignment.BottomStart

    ) {
        Image(
            painter = painterResource(id = mealData.mealImageUri),

            contentDescription = mealData.mealName,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = mealData.mealName,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


data class CarouselItemData(
    val mealName: String,
    val mealImageUri: Int,
    val diet: String
)