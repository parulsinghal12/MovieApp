package com.example.domain.usecase

import com.example.domain.model.MovieDetail
import com.example.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMovieDetailUsecase @Inject constructor(private val repository: MovieRepository) {

    suspend operator fun invoke(movieId: Int): Flow<Response<MovieDetail>> = repository.getMovieDetails(movieId)
}