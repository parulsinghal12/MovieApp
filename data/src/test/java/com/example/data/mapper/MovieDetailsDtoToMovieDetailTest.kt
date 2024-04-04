package com.example.data.mapper

import com.example.data.model.detail.MovieDetailDto
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.Json
import org.junit.Test

class MovieDetailsDtoToMovieDetailTest {

     private val json = Json { ignoreUnknownKeys = true } // Create a Json instance

     @Test
     fun `MovieDetailDto toDomainMovieDetail maps correctly`() {
          println("MovieDetailDto toDomainMovieDetail maps correctly")
          val mockedMovieDetailsJson = getJson("mocked_movie_details.json")
          val movieDetailDto = json.decodeFromString<MovieDetailDto>(mockedMovieDetailsJson)

          val movieDetail = movieDetailDto.toDomainMovieDetail()

/*          assertEquals(85000000, movieDetail.budget)
          assertEquals(5, movieDetail.genres.size) // Assuming there are 5 genres in the JSON
          assertEquals("Action", movieDetail.genres.first().name)
          assertEquals("https://www.dreamworks.com/movies/kung-fu-panda-4", movieDetail.homepage)
          assertEquals(1011985, movieDetail.id)
          assertEquals("en", movieDetail.originalLanguage)
          assertEquals("https://image.tmdb.org/t/p/w500/${movieDetailDto.posterPath}", movieDetail.posterPath)
          assertEquals(3, movieDetail.productionCompanies.size) // Assuming there are 3 production companies
          assertEquals(176000000, movieDetail.revenue)
          assertEquals(94, movieDetail.runtime)
          assertEquals("Released", movieDetail.status)
          assertEquals("Kung Fu Panda 4", movieDetail.title)
          assertFalse(movieDetail.video)
          assertEquals(6.9, movieDetail.rating, 0.01)
          assertEquals(224, movieDetail.voteCount)*/

          // Assert that the domain model has the expected values
          assertEquals(movieDetailDto.budget, movieDetail.budget)
          assertEquals(movieDetailDto.genres.size, movieDetail.genres.size)
          assertEquals(movieDetailDto.genres.first().name, movieDetail.genres.first().name)
          assertEquals(movieDetailDto.homepage, movieDetail.homepage)
          assertEquals(movieDetailDto.id, movieDetail.id)
          assertEquals(movieDetailDto.originalLanguage, movieDetail.originalLanguage)
          assertEquals("https://image.tmdb.org/t/p/w500/${movieDetailDto.posterPath}", movieDetail.posterPath)
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
          return resourceAsStream?.bufferedReader().use { it?.readText() } ?: throw IllegalStateException("Could not load resource: $path")
     }
}