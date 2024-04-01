package com.example.data.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.repository.MovieRepository
import com.example.domain.usecase.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
): MovieRepository {

    override suspend fun getMovies(): Response<MovieList> = dataSource.getMovies()

    override suspend fun getMovieDetails(movieID: Int): Response<MovieDetail> = dataSource.getMoviesDetails(movieID)

}