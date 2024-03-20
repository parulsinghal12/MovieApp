package com.example.presentation.mapper

import com.example.domain.model.MovieList
import com.example.presentation.model.MovieListUiModel

fun MovieList.toMovieListUiModel(): MovieListUiModel {
    return MovieListUiModel(
        movies = this.movies.map { data ->
            data.toMovieUiModel()
        }
    )
}