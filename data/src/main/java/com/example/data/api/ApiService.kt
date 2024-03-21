package com.example.data.api

import com.example.data.model.MovieListDto
import com.example.data.model.detail.MovieDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getMovies(@Query("api_key") apikey: String): Response<MovieListDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieID: Int, @Query("api_key") apikey: String): Response<MovieDetailDto>

}