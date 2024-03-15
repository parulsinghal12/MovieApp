package com.example.data.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dataSource: MovieDataSource,
): MovieRepository {

    override suspend fun getMovies(): Result<MovieList> = dataSource.getMovies()

    override suspend fun getMovieDetails(movieID: Int): Result<MovieDetail> = dataSource.getMoviesDetails(movieID)

}