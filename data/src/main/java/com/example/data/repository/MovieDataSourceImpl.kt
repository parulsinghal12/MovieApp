package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.api.ApiService
import com.example.data.api.executeNetworkRequest
import com.example.data.mapper.toDomainMovieDetail
import com.example.data.mapper.toDomainMovieList
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.usecase.Response
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): MovieDataSource {

    override suspend fun getMovies(): Response<MovieList> {
        return executeNetworkRequest {
            val response = apiService.getMovies(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let { Response.Success(it.toDomainMovieList())
                } ?: Response.Failure(("Received null response body"))
            } else {
                Response.Failure("API call failed with error: ${response.message()}")
            }
        }
    }

    override suspend fun getMoviesDetails(movieId: Int): Response<MovieDetail> {
        return executeNetworkRequest {
            val response = apiService.getMovieDetails(movieId, BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let { Response.Success(it.toDomainMovieDetail())
                } ?: Response.Failure(("Received null response body"))
            } else {
                Response.Failure(("API call failed with error: ${response.message()}"))
            }
        }
    }
}