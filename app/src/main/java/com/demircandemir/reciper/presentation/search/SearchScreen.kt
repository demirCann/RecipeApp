package com.demircandemir.reciper.presentation.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.presentation.common.ListContent


@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onClosedClicked: () -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    val searchQuery by searchViewModel.searchQuery
    val searchState by searchViewModel.searchState.collectAsState()


    Scaffold(
        topBar = {
            SearchTopAppBar(
                text = searchQuery,
                onTextChange = { searchViewModel.updateSearchQuery(it) },
                onClosedClicked = onClosedClicked,
                onSearchClicked = { searchViewModel.searchRecipe(it) }
            )
        },
        content = { paddingValues ->

            when(searchState) {
                is SearchState.Loading -> {
                    // Loading
                }
                is SearchState.Success -> {
                    val searchedMeals = (searchState as SearchState.Success).meals.results
                    ListContent(
                        modifier = Modifier.padding(paddingValues),
                        meals = searchedMeals,
                        navigateToDetail = navigateToDetail,
                        onAddedFavorite = {
                            searchViewModel.addFavorite(it)
                        }
                    )
                }
                is SearchState.Error -> {
                    val message = (searchState as SearchState.Error).message
                }
            }


        }
    )

}