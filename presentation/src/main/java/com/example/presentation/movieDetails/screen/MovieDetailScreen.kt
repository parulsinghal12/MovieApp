package com.example.presentation.movieDetails.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.R
import com.example.presentation.movieDetails.contract.MovieDetailContract
import com.example.presentation.movieDetails.viewModel.MovieDetailsViewModel
import com.example.presentation.ui.components.CustomTextView

@Composable
fun MovieDetailsScreen(movieDetailViewModel: MovieDetailsViewModel = hiltViewModel(), movieID: Int) {

    val resultValue = movieDetailViewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        movieDetailViewModel.sendEvent(MovieDetailContract.ViewIntent.GetMovieDetails(movieID))
    })

    when (resultValue.value) {

        is MovieDetailContract.ViewState.Error -> Toast.makeText(
            context,
            ((resultValue.value as MovieDetailContract.ViewState.Error).message),
            Toast.LENGTH_SHORT
        ).show()

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