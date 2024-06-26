package com.example.data.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.usecase.Response

interface MovieDataSource {
    suspend fun getMovies() : Response<MovieList>
    suspend fun getMoviesDetails(movieId: Int) : Response<MovieDetail>
}