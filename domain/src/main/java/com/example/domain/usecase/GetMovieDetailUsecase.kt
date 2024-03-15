package com.example.domain.usecase

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.repository.MovieRepository
import javax.inject.Inject


class GetMovieDetailUsecase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): Result<MovieDetail> = repository.getMovieDetails(movieId)
}