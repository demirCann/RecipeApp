package com.demircandemir.reciper.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demircandemir.reciper.data.source.network.response.Result

@Database(entities = [Result::class], version = 1)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}