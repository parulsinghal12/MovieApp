package com.example.presentation.movieList.screen

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
import com.example.presentation.movieList.contract.MovieListContract
import com.example.presentation.movieList.viewModel.MoviesViewModel
import com.example.presentation.ui.components.CustomTextView


@Composable
fun MoviesScreen(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
    selectedMovie: (Int) -> Unit
) {
    val resultValue = moviesViewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {

        moviesViewModel.sideEffect.collect { effect ->
            when (effect) {
                is MovieListContract.SideEffect.NavigateToDetailsScreen -> {
                    //update selectedMovie lambda fun
                    selectedMovie(effect.movieId)
                }
            }
        }
    })

    when (resultValue.value) {
        is MovieListContract.ViewState.Error -> Toast.makeText(
            context,
            (resultValue.value as MovieListContract.ViewState.Error).message,
            Toast.LENGTH_SHORT
        ).show()

        is MovieListContract.ViewState.Loading -> {
            Column{
                CustomTextView(
                    text = stringResource(id = R.string.loading),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        is MovieListContract.ViewState.Success -> {
            MoviesListView(movieList = (resultValue.value as MovieListContract.ViewState.Success).data.movies,
                            selectedMovie = { movieId ->
                                moviesViewModel.sendEvent(MovieListContract.ViewIntent.OnMovieItemClicked(movieId)) })
        }
    }
}




