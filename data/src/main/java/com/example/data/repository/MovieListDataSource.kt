package com.example.data.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.usecase.Response
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    suspend fun getMovies() : Flow<Response<MovieList>>
    suspend fun getMoviesDetails(movieId: Int) : Flow<Response<MovieDetail>>
}