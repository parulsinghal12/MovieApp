package com.example.data.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import retrofit2.Response

interface MovieDataSource {
    suspend fun getMovies() : Result<MovieList>
    suspend fun getMoviesDetails(movieId: Int) : Result<MovieDetail>
}