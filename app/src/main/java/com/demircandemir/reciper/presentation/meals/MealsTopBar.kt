package com.demircandemir.reciper.presentation.meals

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.demircandemir.reciper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsTopBar(
    topBarName: String,
    onSearchClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = topBarName) },
        navigationIcon = {
            if (topBarName != "Meals" && topBarName != "Favorites") {
                BackButton{
                    onBackClicked()
                }
            }
        },
        actions = {
            if(topBarName != "Favorites") {
                SearchButton {
                    onSearchClicked()
                }
            }

        }
    )
}

@Composable
fun BackButton(
    onBackClicked: () -> Unit
) {

    IconButton(onClick = { onBackClicked() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_button)
        )
    }
}


@Composable
fun SearchButton(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_button)
        )
    }
}