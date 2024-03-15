package com.example.domain.model

data class Movie(

    val backdropPath: String,

    val id: Int,

    val originalLanguage: String,

    val overview: String,

    val popularity: Double,

    val posterPath: String,

    val releaseDate: String,

    val title: String,

    val rating: Double,

    val voteCount: Int
)
