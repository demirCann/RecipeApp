package com.demircandemir.reciper.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.source.MealRepository
import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.util.DaoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MealRepository
): ViewModel(){

    private val _favoritesState = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
    val favoritesState = _favoritesState.asStateFlow()


    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavorites().collect {
                when (it) {
                    is DaoResult.Success -> {
                        _favoritesState.value = FavoriteState.Success(it.data)
                    }

                    is DaoResult.Error -> {
                        _favoritesState.value = FavoriteState.Error(it.message)
                    }

                    is DaoResult.Loading -> {
                        _favoritesState.value = FavoriteState.Loading
                    }
                }
            }
        }

    }

    fun removeMealFromFavorites(mealId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeMealFromFavorites(mealId)
        }
    }

}


//data class FavoriteState(
//    val isLoading: Boolean = false,
//    val isError: Boolean = false,
//    val result: List<Result>? = null
//)

sealed class FavoriteState {
    data object Loading : FavoriteState()
    data class Success(val favorites: List<Result>) : FavoriteState()
    data class Error(val message: String) : FavoriteState()
}