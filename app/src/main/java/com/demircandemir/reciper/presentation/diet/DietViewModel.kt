package com.demircandemir.reciper.presentation.diet

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.source.MealRepository
import com.demircandemir.reciper.data.source.network.response.MealResponse
import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _mealState = MutableStateFlow<MealState>(MealState.Loading)
    val mealState = _mealState.asStateFlow()

    val diet = savedStateHandle.get<String>("diet")

    init {
        fetchMealsForDiet(diet!!)
    }

    private fun fetchMealsForDiet(diet: String, number: Int = 50) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMealsForDiet(diet, number).collect {
                when (it) {
                    is ApiResult.Success -> {
                        _mealState.value = MealState.Success(it.data!!)
                    }

                    is ApiResult.Error -> {
                        _mealState.value = MealState.Error(it.message!!)
                    }

                    is ApiResult.Loading -> {
                        _mealState.value = MealState.Loading
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

sealed class MealState {
    data object Loading : MealState()
    data class Success(val meals: MealResponse) : MealState()
    data class Error(val message: String) : MealState()
}

