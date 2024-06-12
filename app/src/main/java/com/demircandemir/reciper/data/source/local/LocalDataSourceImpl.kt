package com.demircandemir.reciper.data.source.local

import com.demircandemir.reciper.data.source.network.response.Result
import com.demircandemir.reciper.util.DaoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteDao
): LocalDataSource {
    override fun getAllFavorites(): Flow<DaoResult<List<Result>>> = flow {
        emit(DaoResult.Loading)

        val favorites = dao.getAllFavorites().firstOrNull()
        if (favorites != null) {
            emit(DaoResult.Success(favorites))
        } else {
            emit(DaoResult.Error("Error"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun addToFavorites(result: Result) {
        dao.addToFavorites(result)
    }

    override suspend fun removeMealFromFavorites(mealId: Int) {
        dao.removeMealFromFavorites(mealId)
    }

}