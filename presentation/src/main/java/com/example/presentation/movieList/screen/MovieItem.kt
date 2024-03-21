package com.example.presentation.movieList.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.model.MovieUiModel
import com.example.presentation.ui.components.CustomImageView
import com.example.presentation.ui.components.CustomTextView
import com.example.presentation.ui.components.CustomTextViewBold

@Composable
fun MovieItem(movie: MovieUiModel, selectedMovie: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
        )
    ) {

        Column(
            modifier = Modifier
                .clickable { selectedMovie(movie.id) }
                .fillMaxWidth()
                .padding(1.dp)
        ) {
            CustomImageView(
                data = movie.posterPath,
                contentDescription = movie.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextViewBold(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1
            )

            CustomTextView(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomTextView(
                    text = "Votes: ${movie.voteCount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Row{
                    CustomTextView(
                        text = "Rating: ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    CustomTextViewBold(
                        text = "${movie.rating}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieItem() {
    val mockMovie = MovieUiModel(
        id = 1,
        title = "Mock Movie Title",
        posterPath = "",
        releaseDate = "2022-01-01",
        rating = 8.5,
        voteCount = 1234)

    MovieItem(movie = mockMovie, selectedMovie = { _ -> } )
}