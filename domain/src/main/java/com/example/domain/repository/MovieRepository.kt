package com.example.domain.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList

interface MovieRepository {
    suspend fun getMovies(): Result<MovieList>
    suspend fun getMovieDetails(movieID: Int): Result<MovieDetail>
}