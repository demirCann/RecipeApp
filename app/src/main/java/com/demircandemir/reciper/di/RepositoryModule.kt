package com.demircandemir.reciper.di

import com.demircandemir.reciper.data.source.MealRepository
import com.demircandemir.reciper.data.source.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(
        repository: MealRepositoryImpl
    ): MealRepository
}