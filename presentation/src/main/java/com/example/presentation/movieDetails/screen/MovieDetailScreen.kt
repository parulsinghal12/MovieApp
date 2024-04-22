package com.example.presentation.movieDetails.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.R
import com.example.presentation.movieDetails.contract.MovieDetailContract
import com.example.presentation.movieDetails.viewModel.MovieDetailsViewModel
import com.example.presentation.ui.components.CustomTextView
import com.example.presentation.ui.components.ErrorView

@Composable
fun MovieDetailsScreen(movieDetailViewModel: MovieDetailsViewModel = hiltViewModel()) {

    val resultValue = movieDetailViewModel.viewState.collectAsState()

    when (resultValue.value) {

        is MovieDetailContract.ViewState.Error ->
            ErrorView((resultValue.value as MovieDetailContract.ViewState.Error).message)

        MovieDetailContract.ViewState.Loading -> Column {
            CustomTextView(
                text = stringResource(id = R.string.loading),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally)
            )
        }

        is MovieDetailContract.ViewState.Success -> MovieContentView(
            (resultValue.value as MovieDetailContract.ViewState.Success).data
        )
    }
}