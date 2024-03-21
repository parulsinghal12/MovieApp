package com.example.presentation.model

data class MovieDetailUiModel(
    val budget: Long,
    val genres: List<GenreUi>,
    val homepage: String,
    val id: Int,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val productionCompanies: List<String>,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val title: String,
    val video: Boolean,
    val rating: Double,
    val voteCount: Int
)

data class GenreUi(
    val id: Int,
    val name: String
)