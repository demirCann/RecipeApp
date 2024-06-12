package com.demircandemir.reciper.di

import android.content.Context
import androidx.room.Room
import com.demircandemir.reciper.data.source.local.FavoriteDao
import com.demircandemir.reciper.data.source.local.FavoriteDatabase
import com.demircandemir.reciper.data.source.local.LocalDataSource
import com.demircandemir.reciper.data.source.local.LocalDataSourceImpl
import com.demircandemir.reciper.util.Constants.FAVORITES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): FavoriteDatabase = Room.databaseBuilder(
        context.applicationContext,
        FavoriteDatabase::class.java,
        FAVORITES_DATABASE
    ).build()

    @Provides
    @Singleton
    fun providesLocalDataSource(
        favoriteDatabase: FavoriteDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(dao = favoriteDatabase.favoriteDao())
    }


}