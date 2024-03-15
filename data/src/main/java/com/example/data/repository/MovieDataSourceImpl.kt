package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.api.ApiService
import com.example.data.mapper.toDomainMovieList
import com.example.data.model.MovieDto
import com.example.data.model.MovieListDto
import com.example.data.model.detail.MovieDetailDto
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
): MovieDataSource {

    override suspend fun getMovies(): Result<MovieList> {

        return try {
            val response = apiService.getMovies(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it.toDomainMovieList())
                } ?: Result.failure(Exception("Received null response body"))
            } else {
                Result.failure(Exception("API call failed with error: ${response.message()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Network error: ${e.localizedMessage.orEmpty()}"))
        } catch (e: IOException) {
            Result.failure(Exception("IO error: ${e.localizedMessage.orEmpty()}"))
        }
    }

    override suspend fun getMoviesDetails(movieId: Int): Result<MovieDetail> {
        return try {
            val response = apiService.getMovieDetails(movieId, BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                    //Result.success(it.toDomainMovi ())
                    Result.failure(Exception("To be implemented")) // send empty data for time being
                } ?: Result.failure(Exception("Received null response body"))
            } else {
                Result.failure(Exception("API call failed with error: ${response.message()}"))
            }
        } catch (e: HttpException) {
            Result.failure(Exception("Network error: ${e.localizedMessage.orEmpty()}"))
        } catch (e: IOException) {
            Result.failure(Exception("IO error: ${e.localizedMessage.orEmpty()}"))
        }
    }
}