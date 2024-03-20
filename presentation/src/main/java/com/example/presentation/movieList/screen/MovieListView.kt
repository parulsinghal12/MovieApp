package com.example.presentation.movieList.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentation.model.MovieUiModel

@Composable
fun MoviesListView(
    movieList: List<MovieUiModel>,
    selectedMovie: (Int,String) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),//2 columns
        modifier = modifier,
        contentPadding = PaddingValues(10.dp),
        content = {
            items(movieList) { movie ->
                MovieItem(movie = movie, selectedMovie)
            }
        }
    )
}