package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.api.ApiService
import com.example.data.mapper.toDomainMovieDetail
import com.example.data.mapper.toDomainMovieList
import com.example.data.model.MovieListDto
import com.example.data.model.detail.MovieDetailDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDataSourceImplTest {

    private var apiService: ApiService = mockk()
    private lateinit var movieDataSource: MovieDataSourceImpl
    private val json = Json { ignoreUnknownKeys = true } // Create a Json instance
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)


    @Before
    fun setUp() {
        movieDataSource = MovieDataSourceImpl(apiService, testDispatcher)
    }

    @Test
    fun `getMovies returns success`() = testScope.runTest {
        val mockMovieDataString = getJson("mocked_movies.json")
        val mockMovieData = json.decodeFromString<MovieListDto>(mockMovieDataString)
        val expectedMovieList = mockMovieData.toDomainMovieList() // Use the extension function to convert to domain model

        // Mock API response
        coEvery { apiService.getMovies(BuildConfig.API_KEY) } returns Response.success(mockMovieData)

        // Execute the method under test
        val result = movieDataSource.getMovies().toList()

        // Assert that the result contains the expected success value
        assert(result.size == 1) // Ensure there's exactly one emission
        when (val successResult = result.first()) {
            is com.example.domain.usecase.Response.Success -> assertEquals(expectedMovieList, successResult.data) // Validate success content
            else -> fail("Expected Success, got $successResult")
        }
    }

    @Test
    fun `getMovies handles HttpException`() = testScope.runTest {
        // Mock an HttpException
        coEvery { apiService.getMovies(BuildConfig.API_KEY) } throws HttpException(Response.error<Nothing>(404, mockk(relaxed = true)))

        // Execute the method under test
        val result = movieDataSource.getMovies().toList()

        //Assert
        assert(result.size == 1) // Ensure there's exactly one emission
        when (val errorResult = result.first()) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains("HTTP")) // Validate failure content
            else -> fail("Expected Failure, got $errorResult")
        }
    }

    @Test
    fun `getMovies handles IOException`() = testScope.runTest {
        // Mock an IOException
        coEvery { apiService.getMovies(BuildConfig.API_KEY) } throws IOException("Exception")

        // Execute the method under test
        val result = movieDataSource.getMovies().toList()

        assert(result.size == 1) // Ensure there's exactly one emission
        when (val errorResult = result.first()) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains("IO"))
            else -> fail("Expected Failure, got $errorResult")
        }
    }

    @Test
    fun `getMovieDetails returns success`() = testScope.runTest {
        val movieId = 1011985 // Example movie ID
        val mockMovieDetailString = getJson("mocked_movie_details.json")
        val mockMovieDetail = json.decodeFromString<MovieDetailDto>(mockMovieDetailString)
        val expectedMovieDetail = mockMovieDetail.toDomainMovieDetail()

        // Mock API response
        coEvery { apiService.getMovieDetails(movieId, BuildConfig.API_KEY) } returns Response.success(mockMovieDetail)

        // Execute the method under test
        val result = movieDataSource.getMoviesDetails(movieId).toList()

        // Assert
        assert(result.size == 1) // Ensure there's exactly one emission
        when (val successResult = result.first()) {
            is com.example.domain.usecase.Response.Success -> assertEquals(expectedMovieDetail, successResult.data) // Validate success content
            else -> fail("Expected Success, got $successResult")
        }
    }

    @Test
    fun `getMovieDetails handles HttpException`() = testScope.runTest {
        val movieId = 1011985 // Example movie ID

        // Mock an HttpException
        coEvery { apiService.getMovieDetails(movieId, BuildConfig.API_KEY) } throws HttpException(Response.error<Nothing>(404, mockk(relaxed = true)))

        // Execute the method under test
        val result = movieDataSource.getMoviesDetails(movieId).toList()

        // Assert
        assert(result.size == 1) // Ensure there's exactly one emission
        when (val errorResult = result.first()) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains("HTTP")) // Validate failure message
            else -> fail("Expected Failure, got $errorResult")
        }
    }

    @Test
    fun `getMovieDetails handles IOException`() = testScope.runTest {
        val movieId = 1011985 // Example movie ID

        // Mock an IOException
        coEvery { apiService.getMovieDetails(movieId, BuildConfig.API_KEY) } throws IOException("IO error")

        // Execute the method under test
        val result = movieDataSource.getMoviesDetails(movieId).toList()

        // Assert
        assert(result.size == 1) // Ensure there's exactly one emission
        when (val errorResult = result.first()) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains("IO error"))
            else -> fail("Expected Failure, got $errorResult")
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