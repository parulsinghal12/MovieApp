package com.example.domain.usecase

import com.example.domain.model.MovieList
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieListUsecase @Inject constructor (val repository: MovieRepository) {

    suspend operator fun invoke(): Flow<Response<MovieList>> = repository.getMovies()
}