package com.demircandemir.reciper.presentation.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demircandemir.reciper.data.source.MealRepository
import com.demircandemir.reciper.data.source.network.response.MealResponse
import com.demircandemir.reciper.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _breakfastRecipes = MutableStateFlow<MealState>(MealState.Loading)
    val breakfastRecipes: StateFlow<MealState> = _breakfastRecipes.asStateFlow()

    private val _mainCourseRecipes = MutableStateFlow<MealState>(MealState.Loading)
    val lunchRecipes: StateFlow<MealState> = _mainCourseRecipes.asStateFlow()

    private val _dessertRecipes = MutableStateFlow<MealState>(MealState.Loading)
    val dessertRecipes: StateFlow<MealState> = _dessertRecipes.asStateFlow()

    private val _snackRecipes = MutableStateFlow<MealState>(MealState.Loading)
    val snackRecipes: StateFlow<MealState> = _snackRecipes.asStateFlow()

    private val _soupRecipes = MutableStateFlow<MealState>(MealState.Loading)
    val soupRecipes: StateFlow<MealState> = _soupRecipes.asStateFlow()

    private val _drinkRecipes = MutableStateFlow<MealState>(MealState.Loading)
    val drinkRecipes: StateFlow<MealState> = _drinkRecipes.asStateFlow()


    init {
        fetchMealsForType("breakfast")
        fetchMealsForType("main course")
        fetchMealsForType("dessert")
        fetchMealsForType("snack")
        fetchMealsForType("soup")
        fetchMealsForType("drink")
    }

    private fun fetchMealsForType(type: String, number: Int = 10) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMealsForTypes(type, number).collect {
                when (it) {
                    is ApiResult.Success -> {
                        when (type) {
                            "breakfast" -> _breakfastRecipes.value = MealState.Success(it.data!!)
                            "main course" -> _mainCourseRecipes.value = MealState.Success(it.data!!)
                            "dessert" -> _dessertRecipes.value = MealState.Success(it.data!!)
                            "snack" -> _snackRecipes.value = MealState.Success(it.data!!)
                            "soup" -> _soupRecipes.value = MealState.Success(it.data!!)
                            "drink" -> _drinkRecipes.value = MealState.Success(it.data!!)
                        }
                    }

                    is ApiResult.Error -> {
                        when (type) {
                            "breakfast" -> _breakfastRecipes.value =
                                MealState.Error(it.message ?: "An error occurred")

                            "main course" -> _mainCourseRecipes.value =
                                MealState.Error(it.message ?: "An error occurred")

                            "dessert" -> _dessertRecipes.value =
                                MealState.Error(it.message ?: "An error occurred")

                            "snack" -> _snackRecipes.value =
                                MealState.Error(it.message ?: "An error occurred")

                            "soup" -> _soupRecipes.value =
                                MealState.Error(it.message ?: "An error occurred")

                            "drink" -> _drinkRecipes.value =
                                MealState.Error(it.message ?: "An error occurred")
                        }
                    }

                    is ApiResult.Loading -> {
                        when (type) {
                            "breakfast" -> _breakfastRecipes.value = MealState.Loading
                            "main course" -> _mainCourseRecipes.value = MealState.Loading
                            "dessert" -> _dessertRecipes.value = MealState.Loading
                            "snack" -> _snackRecipes.value = MealState.Loading
                            "soup" -> _soupRecipes.value = MealState.Loading
                            "drink" -> _drinkRecipes.value = MealState.Loading
                        }
                    }

                }
            }
        }
    }
}


sealed class MealState {
    data object Loading : MealState()
    data class Success(val meals: MealResponse) : MealState()
    data class Error(val message: String) : MealState()
}