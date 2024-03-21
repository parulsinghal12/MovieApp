package com.example.presentation.mapper

import com.example.domain.model.MovieDetail
import com.example.presentation.model.GenreUi
import com.example.presentation.model.MovieDetailUiModel
import kotlin.math.floor

fun MovieDetail.toMovieDetailUiModel(): MovieDetailUiModel {
    return MovieDetailUiModel(
        budget = budget,
        genres = genres.map{ GenreUi(it.id, it.name) },
        homepage = homepage,
        id = id,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        productionCompanies = productionCompanies,
        revenue = revenue,
        runtime = runtime,
        status = status,
        title = title,
        video = video,
        rating = floor(rating * 10) / 10,
        voteCount = voteCount
    )
}