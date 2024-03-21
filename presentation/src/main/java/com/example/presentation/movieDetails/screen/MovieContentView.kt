package com.example.presentation.movieDetails.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.presentation.model.MovieDetailUiModel

@Composable
fun MovieContentView(movieDetail: MovieDetailUiModel){
    Column {
        Text(text = movieDetail.toString())
    }
}