package com.example.presentation.mapper

import com.example.domain.model.Movie
import com.example.presentation.MockData
import com.example.presentation.model.MovieUiModel
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.math.floor

class ToMovieUiModelTest {

    @Test
    fun `map Movie to MovieUiModel correctly`() {
        // Given
        val domainModel: Movie = MockData.mockMovieList.movies.first()

        // When
        val uiModel: MovieUiModel = domainModel.toMovieUiModel()

        // Then
        assertEquals(domainModel.id, uiModel.id)
        assertEquals(domainModel.posterPath, uiModel.posterPath)
        assertEquals(domainModel.releaseDate, uiModel.releaseDate)
        assertEquals(domainModel.title, uiModel.title)
        assertEquals(floor(domainModel.rating * 10) / 10, uiModel.rating, 0.0)
        assertEquals(domainModel.voteCount, uiModel.voteCount)
    }
}