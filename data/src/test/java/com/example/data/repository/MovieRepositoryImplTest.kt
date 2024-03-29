package com.example.data.repository

import com.example.data.mapper.toDomainMovieDetail
import com.example.data.mapper.toDomainMovieList
import com.example.data.model.MovieListDto
import com.example.data.model.detail.MovieDetailDto
import com.example.domain.repository.MovieRepository
import com.example.domain.usecase.Response
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private lateinit var movieDataSource: MovieDataSource
    private lateinit var movieRepository: MovieRepository
    private val json = Json { ignoreUnknownKeys = true }
    private lateinit var mockMovieListJsonString: String
    private lateinit var mockMovieDetailJsonString: String

    @Before
    fun setUp() {
        movieDataSource = mockk()
        movieRepository = MovieRepositoryImpl(movieDataSource)
        mockMovieListJsonString = getJson("mocked_movies.json")
        mockMovieDetailJsonString = getJson("mocked_movie_details.json")
    }

    @Test
    fun `getMovies returns success`() = runTest {
        //Mock data
        val mockMovieListDto = json.decodeFromString<MovieListDto>(mockMovieListJsonString)
        val expectedMovieList = mockMovieListDto.toDomainMovieList() // Convert to domain model

        coEvery { movieDataSource.getMovies() } returns flowOf(Response.Success(expectedMovieList))

        // Execute the method under test
        val resultList = movieRepository.getMovies().toList()

        // Assert the result
        assertEquals(1, resultList.size)
        when (val result = resultList.first()) {
            is Response.Success -> assertEquals(expectedMovieList, result.data)
            else -> fail("Expected Success, got $result")
        }
    }

    @Test
    fun `getMovieDetails returns success`() = runTest {
        //Mock data
        val mockMovieDetailDto = json.decodeFromString<MovieDetailDto>(mockMovieDetailJsonString)
        val expectedMovieDetail = mockMovieDetailDto.toDomainMovieDetail() // Convert to domain model

        val movieId = 123
        coEvery { movieDataSource.getMoviesDetails(movieId) } returns flowOf(Response.Success(expectedMovieDetail))

        // Execute the method under test
        val resultDetails = movieRepository.getMovieDetails(movieId).toList()

        // Assert the result
        assertEquals(1, resultDetails.size)
        when (val result = resultDetails.first()) {
            is Response.Success -> assertEquals(expectedMovieDetail, result.data)
            else -> fail("Expected Success, got $result")
        }
    }
    @After
    fun tearDown() {

    }

    private fun getJson(path: String): String {
        val resourceAsStream = this::class.java.classLoader?.getResourceAsStream(path)
        return resourceAsStream?.bufferedReader().use { it?.readText() } ?: ""
    }
}