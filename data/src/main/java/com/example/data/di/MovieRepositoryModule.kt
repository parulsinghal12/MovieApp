package com.example.data.di

import com.example.data.repository.MovieRepositoryImpl
import com.example.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieRepositoryModule {

    @Binds
    abstract fun bindMovieRepository (movieRepositoryImpl: MovieRepositoryImpl) : MovieRepository
}