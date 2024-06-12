package com.demircandemir.reciper.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.source.MealRepository
import com.demircandemir.reciper.data.source.network.response.MealResponse
import com.demircandemir.reciper.data.source.network.response.RecipeAutoComplete
import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Loading)
    val searchState = _searchState.asStateFlow()

    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun searchRecipe(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchRecipes(query).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        _searchState.value = SearchState.Success(result.data!!)
                    }

                    is ApiResult.Error -> {
                        _searchState.value = SearchState.Error(result.message!!)
                    }

                    ApiResult.Loading -> {
                        _searchState.value = SearchState.Loading
                    }


                }

            }

        }
    }

    fun addFavorite(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(result)
        }
    }


}

sealed class SearchState {
    data object Loading : SearchState()
    data class Success(val meals: MealResponse) : SearchState()
    data class Error(val message: String) : SearchState()
}
