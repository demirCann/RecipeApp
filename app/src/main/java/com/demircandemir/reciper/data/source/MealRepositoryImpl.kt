package com.demircandemir.reciper.data.source

import com.demircandemir.reciper.data.source.local.LocalDataSource
import com.demircandemir.reciper.data.source.network.RemoteDataSource
import com.demircandemir.reciper.data.source.network.response.MealDetailResponse
import com.demircandemir.reciper.data.source.network.response.MealResponse
import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.util.ApiResult
import com.demircandemir.reciper.util.DaoResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : MealRepository {
    override suspend fun getMealsForTypes(type: String, number: Int): Flow<ApiResult<MealResponse>> {
        return remoteDataSource.getMealsForTypes(type, number)
    }

    override suspend fun getMealsForDiet(diet: String, number: Int): Flow<ApiResult<MealResponse>> {
        return remoteDataSource.getMealsForDiet(diet, number)
    }

    override suspend fun getMealDetails(id: Int): Flow<ApiResult<MealDetailResponse>> {
        return remoteDataSource.getMealDetails(id)
    }

    override fun getAllFavorites(): Flow<DaoResult<List<Result>>> {
        return localDataSource.getAllFavorites()
    }

    override suspend fun addToFavorites(result: Result) {
        localDataSource.addToFavorites(result)
    }

    override suspend fun removeMealFromFavorites(mealId: Int) {
        localDataSource.removeMealFromFavorites(mealId)
    }

}