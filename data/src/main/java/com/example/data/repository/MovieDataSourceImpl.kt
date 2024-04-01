package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.api.ApiService
import com.example.data.di.IoDispatcher
import com.example.data.mapper.toDomainMovieDetail
import com.example.data.mapper.toDomainMovieList
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.usecase.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): MovieDataSource {

    override suspend fun getMovies(): Response<MovieList> {
        return try {
            val response = apiService.getMovies(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                     Response.Success(it.toDomainMovieList())
                } ?: Response.Failure(("Received null response body"))
            } else {
                Response.Failure(("API call failed with error: ${response.message()}"))
            }
        } catch (e: HttpException) {
            Response.Failure(("Network error: ${e.localizedMessage.orEmpty()}"))
        } catch (e: IOException) {
            Response.Failure(("IO error: ${e.localizedMessage.orEmpty()}"))
        }
    }

    override suspend fun getMoviesDetails(movieId: Int): Response<MovieDetail> {
         return try {
            val response = apiService.getMovieDetails(movieId, BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                    Response.Success(it.toDomainMovieDetail())
                    //Result.failure(Exception("To be implemented")) //send empty data for time being
                } ?: Response.Failure(("Received null response body"))
            } else {
                Response.Failure(("API call failed with error: ${response.message()}"))
            }
        } catch (e: HttpException) {
             Response.Failure(("Network error: ${e.localizedMessage.orEmpty()}"))
        } catch (e: IOException) {
             Response.Failure(("IO error: ${e.localizedMessage.orEmpty()}"))
        }
    }
}