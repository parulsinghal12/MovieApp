package com.example.domain.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.usecase.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<Response<MovieList>>
    suspend fun getMovieDetails(movieID: Int): Flow<Response<MovieDetail>>
}