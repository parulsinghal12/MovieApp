package com.example.presentation.mapper

import com.example.domain.model.Movie
import com.example.presentation.model.MovieUiModel
import kotlin.math.floor

fun Movie.toMovieUiModel(): MovieUiModel{
    return MovieUiModel(
        id = id,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        rating = floor(rating * 10) / 10,
        voteCount = voteCount)
}