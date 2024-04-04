package com.example.presentation.mapper

import com.example.domain.model.MovieDetail
import com.example.presentation.MockData
import com.example.presentation.model.MovieDetailUiModel
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlin.math.floor

class ToMovieDetailUiModelTest {

    @Test
    fun `map MovieDetail to MovieDetailUiModel correctly`() {
        // Given
        val domainModel: MovieDetail = MockData.mockMovieDetail

        // When
        val uiModel: MovieDetailUiModel = domainModel.toMovieDetailUiModel()
        // Then
        assertEquals(domainModel.id, uiModel.id)
        assertEquals(domainModel.title, uiModel.title)
        assertEquals(domainModel.posterPath, uiModel.posterPath)
        assertEquals(domainModel.overview, uiModel.overview)
        assertEquals(domainModel.budget, uiModel.budget)
        assertEquals(domainModel.homepage, uiModel.homepage)
        assertEquals(domainModel.originalLanguage, uiModel.originalLanguage)
        assertEquals(domainModel.productionCompanies, uiModel.productionCompanies)
        assertEquals(domainModel.revenue, uiModel.revenue)
        assertEquals(domainModel.runtime, uiModel.runtime)
        assertEquals(domainModel.status, uiModel.status)
        assertEquals(domainModel.video, uiModel.video)
        assertEquals(floor(domainModel.rating * 10) / 10, uiModel.rating, 0.0)
        assertEquals(domainModel.voteCount, uiModel.voteCount)

        // Mapping of genres from domain to UI model, comparing the sizes and individual elements
        assertEquals(domainModel.genres.size, uiModel.genres.size)
        domainModel.genres.zip(uiModel.genres).forEach { (domainGenre, uiGenre) ->
            assertEquals(domainGenre.id, uiGenre.id)
            assertEquals(domainGenre.name, uiGenre.name)
        }
    }
}