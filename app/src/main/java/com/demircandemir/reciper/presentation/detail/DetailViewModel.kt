package com.demircandemir.reciper.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.source.MealRepository
import com.demircandemir.reciper.data.source.network.response.MealDetailResponse
import com.demircandemir.reciper.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _mealState = MutableStateFlow<MealState>(MealState.Loading)
    val mealState = _mealState.asStateFlow()

    val id = savedStateHandle.get<Int>("recipeId") ?: 0

    init {
        fetchMealDetail(id)
    }

    private fun fetchMealDetail(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getMealDetails(id).collect {
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
}


sealed class MealState {
    data object Loading : MealState()
    data class Success(val meals: MealDetailResponse) : MealState()
    data class Error(val message: String) : MealState()
}