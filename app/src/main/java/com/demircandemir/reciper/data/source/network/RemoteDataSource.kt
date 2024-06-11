package com.demircandemir.reciper.data.source.network

import com.demircandemir.reciper.data.source.network.response.MealDetailResponse
import com.demircandemir.reciper.data.source.network.response.MealResponse
import com.demircandemir.reciper.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getMealsForTypes(type: String, number: Int): Flow<ApiResult<MealResponse>>

    suspend fun getMealsForDiet(diet: String, number: Int): Flow<ApiResult<MealResponse>>

    suspend fun getMealDetails(id: Int): Flow<ApiResult<MealDetailResponse>>
}