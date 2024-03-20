package com.example.data.mapper

import com.example.data.model.MovieDto
import com.example.domain.model.Movie

fun MovieDto.toDomainMovie () : Movie {
    return Movie(
        backdropPath = backdropPath,
        id = id,
        originalLanguage = originalLanguage,
        overview = overview,
        popularity = popularity,
        posterPath = "https://image.tmdb.org/t/p/w500/$posterPath",
        releaseDate = releaseDate,
        title = title,
        rating = rating,
        voteCount = voteCount)
}