package com.example.domain.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.usecase.Response

interface MovieRepository {
    suspend fun getMovies(): Response<MovieList>
    suspend fun getMovieDetails(movieID: Int): Response<MovieDetail>
}