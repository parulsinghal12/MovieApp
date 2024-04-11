package com.example.data.mapper

import com.example.data.model.detail.MovieDetailDto
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.Json
import org.junit.Test

class MovieDetailsDtoToMovieDetailTest {

     private val json = Json { ignoreUnknownKeys = true } // Create a Json instance

     @Test
     fun `MovieDetailDto toDomainMovieDetail maps correctly`() {
          val mockedMovieDetailsJson = getJson(MOVIE_DETAILS_JSON_FILE)
          val movieDetailDto = json.decodeFromString<MovieDetailDto>(mockedMovieDetailsJson)

          val movieDetail = movieDetailDto.toDomainMovieDetail()

          // Assert that the domain model has the expected values
          assertEquals(movieDetailDto.budget, movieDetail.budget)
          assertEquals(movieDetailDto.genres.size, movieDetail.genres.size)
          assertEquals(movieDetailDto.genres.first().name, movieDetail.genres.first().name)
          assertEquals(movieDetailDto.homepage, movieDetail.homepage)
          assertEquals(movieDetailDto.id, movieDetail.id)
          assertEquals(movieDetailDto.originalLanguage, movieDetail.originalLanguage)
          assertEquals("$POSTER_PATH_PREFIX${movieDetailDto.posterPath}", movieDetail.posterPath)
          assertEquals(movieDetailDto.productionCompanies.map { it.name }, movieDetail.productionCompanies)
          assertEquals(movieDetailDto.revenue, movieDetail.revenue)
          assertEquals(movieDetailDto.runtime, movieDetail.runtime)
          assertEquals(movieDetailDto.status, movieDetail.status)
          assertEquals(movieDetailDto.title, movieDetail.title)
          assertEquals(movieDetailDto.video, movieDetail.video)
          assertEquals(movieDetailDto.rating, movieDetail.rating, 0.01)
          assertEquals(movieDetailDto.voteCount, movieDetail.voteCount)
     }

     private fun getJson(path: String): String {
          val resourceAsStream = this::class.java.classLoader?.getResourceAsStream(path)
          return resourceAsStream?.bufferedReader().use { it?.readText() } ?: throw IllegalStateException(
               String.format(ILLEGAL_EXCEPTION,path)
          )
     }

     companion object {
          private const val MOVIE_DETAILS_JSON_FILE = "mocked_movie_details.json"
          private const val POSTER_PATH_PREFIX = "https://image.tmdb.org/t/p/w500/"
          private const val ILLEGAL_EXCEPTION = "Could not load resource: %s"
     }
}