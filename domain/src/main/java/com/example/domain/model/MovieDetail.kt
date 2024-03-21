package com.example.domain.model

data class MovieDetail(
    val budget: Long,
    val genres: List<Genre>,
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

data class Genre(
    val id: Int,
    val name: String
)



