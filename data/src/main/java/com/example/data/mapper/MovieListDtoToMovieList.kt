package com.example.data.mapper


import com.example.data.model.MovieListDto
import com.example.domain.model.MovieList

fun MovieListDto.toDomainMovieList() : MovieList {
    return MovieList(
        movies = this.movieList.map { data ->
            data.toDomainMovie()
        }
    )
}