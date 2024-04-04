package com.example.data.mapper

import com.example.data.model.MovieListDto
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.Json
import org.junit.Test

class MovieDtoToMovieTest {

    private val json = Json { ignoreUnknownKeys = true } // Create a Json instance to parse JSON

    @Test
    fun `MovieDto toDomainMovie maps correctly`() {
        // Use the mocked_movies.json for a list of movies
        val mockedMoviesJson = getJson("mocked_movies.json")

        val moviesDto = json.decodeFromString<MovieListDto>(mockedMoviesJson)

        val domainMovie = moviesDto.toDomainMovieList().movies.first()

        // Asserting various fields based on the first movie in your mocked data
        assertEquals(moviesDto.movieList.first().backdropPath, domainMovie.backdropPath)
        assertEquals(moviesDto.movieList.first().id, domainMovie.id)
        assertEquals(moviesDto.movieList.first().originalLanguage, domainMovie.originalLanguage)
        assertEquals(moviesDto.movieList.first().overview, domainMovie.overview)
        assertEquals(moviesDto.movieList.first().popularity, domainMovie.popularity)
        assertEquals("https://image.tmdb.org/t/p/w500//kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg", domainMovie.posterPath)
        assertEquals(moviesDto.movieList.first().releaseDate, domainMovie.releaseDate)
        assertEquals(moviesDto.movieList.first().title, domainMovie.title)
        assertEquals(moviesDto.movieList.first().rating, domainMovie.rating)
        assertEquals(moviesDto.movieList.first().voteCount, domainMovie.voteCount)
    }

    private fun getJson(path: String): String {
        val resourceAsStream = this::class.java.classLoader?.getResourceAsStream(path)
        return resourceAsStream?.bufferedReader().use { it?.readText() } ?: ""
    }
}
