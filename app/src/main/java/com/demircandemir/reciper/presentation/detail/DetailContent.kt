package com.demircandemir.reciper.presentation.detail

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.demircandemir.reciper.data.source.network.response.MealDetailResponse
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    meal: MealDetailResponse,
    onFinishedClicked : () -> Unit
) {


    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
        sheetPeekHeight = 240.dp,
        sheetContent = {
            LineIndicatorExample(
                meal = meal,
                onFinishedClicked = {
                    onFinishedClicked()
                }
            )
        },
        content = { paddingValues ->
            BackgroundContent(
                meal = meal,
                paddingValues = paddingValues,
                onCloseClicked = {
                    onFinishedClicked()
                }
            )
        }
    )
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LineIndicatorExample(
    meal: MealDetailResponse,
    onFinishedClicked : () -> Unit
) {
    Column(modifier  = Modifier.heightIn(min = 100.dp, max = 500.dp)){


        val pageCount = meal.analyzedInstructions.size
        val pagerState = rememberPagerState(pageCount = { pageCount + 1 })


        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            beyondBoundsPageCount = 4,
            state = pagerState)
        {
            PagerSampleItem(
                meal = meal,
                pageNumber = pageCount,
                pagerState = pagerState,
                onFinishedClicked = {
                    onFinishedClicked()
                }
            )
        }

    }
}


@Composable
fun BackgroundContent(
    meal: MealDetailResponse,
    paddingValues: PaddingValues,
    onCloseClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.surface)
    ) {

        AsyncImage(
            model = meal.image,
            contentDescription = "Meat Food",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopStart),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(
                modifier = Modifier.padding(all = 8.dp),
                onClick = { onCloseClicked() }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerSampleItem(
    meal: MealDetailResponse,
    pageNumber: Int,
    pagerState: androidx.compose.foundation.pager.PagerState,
    onFinishedClicked : () -> Unit
) {


    if (pagerState.currentPage == 0) {
        StartPage(
            meal = meal,
            pagerState = pagerState
        )

    } else {
        StepPage(
            meal = meal,
            pageNumber = pageNumber,
            pagerState = pagerState,
            onFinishedClicked = {
                onFinishedClicked()
            }
        )
    }





}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StartPage(
    meal: MealDetailResponse,
    pagerState: androidx.compose.foundation.pager.PagerState
) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 16.dp),
    ) {

        Text(
            text = meal.title,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${meal.id} kcal",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Calories",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${meal.id} g",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Protein",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${meal.id} g",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Carbs",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${meal.id} g",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Fat",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

        }



        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleMedium,
            )
        }


        meal.extendedIngredients.forEach {
            Row(
                modifier = Modifier
                    .padding(start = 18.dp, top = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.94f),
                shape = RoundedCornerShape(100.dp),
            ) {
                Text(text = "Start Cooking")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StepPage(
    meal: MealDetailResponse,
    pageNumber: Int,
    pagerState: androidx.compose.foundation.pager.PagerState,
    onFinishedClicked : () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Step ${pagerState.currentPage}",
            style = MaterialTheme.typography.headlineLarge,
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageNumber) { iteration ->
                val color =
                    if (pagerState.currentPage - 1 == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
                val tintColor =
                    if (pagerState.currentPage - 1 == iteration) Color.White else MaterialTheme.colorScheme.primary
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        .background(color)
                        .size(32.dp)
                ) {
                    Text(
                        text = (iteration + 1).toString(),
                        modifier = Modifier.align(Alignment.Center),
                        color = tintColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(32.dp))

        meal.analyzedInstructions.firstOrNull()?.steps?.get(pagerState.currentPage - 1)?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                text = it.step,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (pagerState.currentPage == pagerState.pageCount - 1) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        Log.d("Pager", "PagerStatePrevious: ${pagerState.pageCount}")
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    modifier = Modifier
                        .width(170.dp),
                    colors = ButtonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.DarkGray,
                        disabledContentColor = Color.DarkGray,
                        disabledContainerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(100.dp),
                ) {
                    Text(text = "Previous")
                }


                Button(
                    onClick = {
                        Log.d("Pager", "PagerStateNext: ${pagerState.currentPage}")
                        coroutineScope.launch {
                            onFinishedClicked()
                        }
                    },
                    modifier = Modifier
                        .width(170.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(100.dp),
                ) {
                    Text(text = "Finish Cooking")
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        Log.d("Pager", "PagerStatePrevious: ${pagerState.pageCount}")
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },
                    modifier = Modifier
                        .width(170.dp),
                    colors = ButtonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.DarkGray,
                        disabledContentColor = Color.DarkGray,
                        disabledContainerColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(100.dp),
                ) {
                    Text(text = "Previous")
                }


                Button(
                    onClick = {
                        Log.d("Pager", "PagerStateNext: ${pagerState.currentPage}")
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier
                        .width(170.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(100.dp),
                ) {
                    Text(text = "Next")
                }
            }
        }

    }

}