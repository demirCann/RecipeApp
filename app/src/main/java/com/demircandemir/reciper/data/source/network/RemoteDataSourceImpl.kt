package com.demircandemir.reciper.data.source.network

import com.demircandemir.reciper.data.source.network.response.MealDetailResponse
import com.demircandemir.reciper.data.source.network.response.MealResponse
import com.demircandemir.reciper.util.ApiResult
import com.demircandemir.reciper.util.apiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val mealApi: MealApi
): RemoteDataSource {

    override suspend fun getMealsForTypes(type: String, number: Int): Flow<ApiResult<MealResponse>> = apiFlow {
        mealApi.getMealsForTypes(type = type, number = number)
    }

    override suspend fun getMealsForDiet(diet: String, number: Int): Flow<ApiResult<MealResponse>> = apiFlow {
        mealApi.getMealsForDiet(type = diet, number = number)
    }

    override suspend fun getMealDetails(id: Int): Flow<ApiResult<MealDetailResponse>> = apiFlow {
        mealApi.getMealDetails(id = id)
    }

    override suspend fun searchRecipes(query: String): Flow<ApiResult<MealResponse>> = apiFlow {
        mealApi.searchRecipes(query = query)
    }

}