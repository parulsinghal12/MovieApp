package com.example.data.mapper

import com.example.data.model.MovieListDto
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.Json
import org.junit.Test

class MovieListDtoToMovieListTest {

    private val json = Json { ignoreUnknownKeys = true } // Create a Json instance to parse JSON

    @Test
    fun `MovieListDto toDomainMovieList maps correctly`() {
        // Use the mocked_movies.json for a list of movies
        val mockedMoviesJson = getJson()
        val moviesListDto = json.decodeFromString<MovieListDto>(mockedMoviesJson)

        val domainMovieList = moviesListDto.toDomainMovieList()
        assertEquals(moviesListDto.movieList.size,domainMovieList.movies.size )

    }

    private fun getJson(): String {
        val resourceAsStream = this::class.java.classLoader?.getResourceAsStream(MOVIE_JSON_FILE)
        return resourceAsStream?.bufferedReader().use { it?.readText() } ?: ""
    }

    companion object {
        private const val MOVIE_JSON_FILE = "mocked_movies.json"
    }
}
