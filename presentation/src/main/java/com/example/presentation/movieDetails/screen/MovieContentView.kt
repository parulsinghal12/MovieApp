package com.example.presentation.movieDetails.screen
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.model.GenreUi
import com.example.presentation.model.MovieDetailUiModel
import com.example.presentation.ui.components.CustomImageView
import com.example.presentation.ui.components.CustomTextView
import com.example.presentation.ui.components.CustomTextViewBold
import com.example.presentation.ui.components.CustomTextViewHeading

@Composable
fun MovieContentView(movieDetail: MovieDetailUiModel) {
    val configuration = LocalConfiguration.current
    val scrollState = rememberScrollState()

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Poster Image taking half the screen width in landscape
            CustomImageView(
                data = movieDetail.posterPath,
                contentDescription = movieDetail.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            // Movie Details next to the poster
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MovieDetailAttributes(movieDetail)
                Divider()
                CustomTextViewBold(
                    text = "Overview",
                    style = MaterialTheme.typography.headlineSmall,
                )
                CustomTextView(
                    text = movieDetail.overview,
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkGray
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Poster Image
            CustomImageView(
                data = movieDetail.posterPath,
                contentDescription = movieDetail.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(Modifier.height(16.dp))
            MovieDetailAttributes(movieDetail)
            Divider()
            CustomTextViewBold(
                text = "Overview",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
            )
            CustomTextView(
                text = movieDetail.overview,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkGray
            )
        }
    }
}

@Composable
fun MovieDetailAttributes(movieDetail: MovieDetailUiModel) {
    Divider(modifier = Modifier.padding(bottom = 2.dp))
    CustomTextViewHeading(
        text = movieDetail.title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(bottom = 4.dp)
    )
    Divider(modifier = Modifier.padding(bottom = 2.dp))
    MovieAttribute("Language", mapLanguageCodeToName1(movieDetail.originalLanguage))
    MovieAttribute("Genres", movieDetail.genres.joinToString { it.name })
    MovieAttribute("Duration", "${movieDetail.runtime} minutes")
    MovieAttribute("Rating", "${movieDetail.rating} (${movieDetail.voteCount} votes)")
}

@Composable
fun MovieAttribute(label: String, value: String) {
    Row(verticalAlignment = Alignment.Top ) {
        CustomTextViewBold(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge.copy(color = DarkGray),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        CustomTextView(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = DarkGray
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun mapLanguageCodeToName1(languageCode: String): String {
    return when (languageCode) {
        "en" -> "English"
        else -> languageCode.uppercase()
    }
}

// Provide some sample data to populate the preview
private fun sampleMovieDetailUiModel() = MovieDetailUiModel(
    budget = 85000000,
    genres = listOf(
        GenreUi(id = 28, name = "Action"),
        GenreUi(id = 12, name = "Adventure"),
        GenreUi(id = 16, name = "Animation"),
        GenreUi(id = 35, name = "Comedy"),
        GenreUi(id = 10751, name = "Family")
    ),
    homepage = "https://www.dreamworks.com/movies/kung-fu-panda-4",
    id = 1011985,
    originalLanguage = "en",
    overview = "Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.",
    posterPath = "https://image.tmdb.org/t/p/w500//wkfG7DaExmcVsGLR4kLouMwxeT5.jpg",
    productionCompanies = listOf("DreamWorks Animation", "Universal Pictures", "Pearl Studio"),
    revenue = 176000000,
    runtime = 94,
    status = "Released",
    title = "Kung Fu Panda 4",
    video = false,
    rating = 6.9,
    voteCount = 214
)

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun MovieContentViewPreview() {
    MovieContentView(movieDetail = sampleMovieDetailUiModel())
}
