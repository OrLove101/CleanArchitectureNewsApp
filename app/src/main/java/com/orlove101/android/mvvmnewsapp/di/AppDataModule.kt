package com.orlove101.android.mvvmnewsapp.di

import com.orlove101.android.mvvmnewsapp.data.repository.NewsRepositoryImpl
import com.orlove101.android.mvvmnewsapp.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module()
abstract class AppDataModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repositoryImpl: NewsRepositoryImpl): NewsRepository
}