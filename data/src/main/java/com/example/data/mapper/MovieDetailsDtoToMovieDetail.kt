package com.example.data.mapper

import com.example.data.model.detail.MovieDetailDto
import com.example.domain.model.Genre
import com.example.domain.model.MovieDetail

fun MovieDetailDto.toDomainMovieDetail () : MovieDetail {
    return MovieDetail(
         budget = budget,
         genres = genres.map { Genre(it.id, it.name) },
         homepage = homepage,
         id = id,
         originalLanguage = originalLanguage,
         overview = overview,
         posterPath = posterPath,
         productionCompanies = productionCompanies.map { it.name },
         revenue = revenue,
         runtime = runtime,
         status = status,
         title = title,
         video = video,
         rating = rating,
         voteCount = voteCount
    )
}