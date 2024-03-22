package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.api.ApiService
import com.example.data.mapper.toDomainMovieDetail
import com.example.data.mapper.toDomainMovieList
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.usecase.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
): MovieDataSource {

    override suspend fun getMovies(): Flow<Response<MovieList>> = flow {
        try {
            val response = apiService.getMovies(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit (Response.Success(it.toDomainMovieList()))
                } ?:
                emit (Response.Failure("Received null response body"))
            } else {
                emit(Response.Failure("API call failed with error: ${response.message()}"))
            }
        } catch (e: HttpException) {
            emit(Response.Failure("Network error: ${e.localizedMessage.orEmpty()}"))
        } catch (e: IOException) {
            emit(Response.Failure("IO error: ${e.localizedMessage.orEmpty()}"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMoviesDetails(movieId: Int): Flow<Response<MovieDetail>> = flow {
         try {
            val response = apiService.getMovieDetails(movieId, BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit (Response.Success(it.toDomainMovieDetail()))
                    //Result.failure(Exception("To be implemented")) //send empty data for time being
                } ?: emit (Response.Failure(("Received null response body")))
            } else {
                emit (Response.Failure(("API call failed with error: ${response.message()}")))
            }
        } catch (e: HttpException) {
             emit (Response.Failure(("Network error: ${e.localizedMessage.orEmpty()}")))
        } catch (e: IOException) {
             emit (Response.Failure(("IO error: ${e.localizedMessage.orEmpty()}")))
        }
    }.flowOn(Dispatchers.IO)
}