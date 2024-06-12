package com.demircandemir.reciper.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demircandemir.reciper.data.source.network.response.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites_table")
    fun getAllFavorites(): Flow<List<Result>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(result: Result)

    @Query("DELETE FROM favorites_table WHERE id = :mealId")
    suspend fun removeMealFromFavorites(mealId: Int)


}