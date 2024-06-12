package com.demircandemir.reciper.data.source.local

import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.util.DaoResult
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAllFavorites(): Flow<DaoResult<List<Result>>>

    suspend fun addToFavorites(result: Result)

    suspend fun removeMealFromFavorites(mealId: Int)


}