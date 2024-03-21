package com.example.presentation.movieDetails.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.movieList.viewModel.MoviesViewModel

@Composable
fun MovieDetailsScreen(movieDetailViewModel: MoviesViewModel = hiltViewModel(), movieID: Int) {
    Column {
        Text(text = "second screen")
        if (movieID!= 0)
            Text(text = "{$movieID}")
    }


}