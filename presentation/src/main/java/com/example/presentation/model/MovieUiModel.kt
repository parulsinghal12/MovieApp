package com.example.presentation.model

data class MovieUiModel(

    val id: Int,

    val posterPath: String,

    val releaseDate: String,

    val title: String,

    val rating: Double,

    val voteCount: Int
)