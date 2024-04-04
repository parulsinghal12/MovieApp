package com.example.presentation.mapper

import com.example.domain.model.MovieList
import com.example.presentation.MockData
import com.example.presentation.model.MovieListUiModel
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.math.floor

class ToMovieListUiModelTest {

    @Test
    fun `map MovieList to MovieListUiModel correctly`() {
        // Given
        val domainModel: MovieList = MockData.mockMovieList

        // When
        val uiModel: MovieListUiModel = domainModel.toMovieListUiModel()

        // Then
        assertEquals(domainModel.movies.size, uiModel.movies.size)

        domainModel.movies.zip(uiModel.movies).forEach { (domainMovie, uiMovie) ->
            assertEquals(domainMovie.id, uiMovie.id)
            assertEquals(domainMovie.posterPath, uiMovie.posterPath)
            assertEquals(domainMovie.releaseDate, uiMovie.releaseDate)
            assertEquals(domainMovie.title, uiMovie.title)
            assertEquals(floor(domainMovie.rating * 10) / 10, uiMovie.rating, 0.0)
            assertEquals(domainMovie.voteCount, uiMovie.voteCount)
        }
    }
}