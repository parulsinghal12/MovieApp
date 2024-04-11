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
        movieDataSource = MovieDataSourceImpl(apiService)
    }

    @Test
    fun `getMovies returns success`() = testScope.runTest {
        val mockMovieDataString = getJson(MOVIES_JSON_FILE)
        val mockMovieData = json.decodeFromString<MovieListDto>(mockMovieDataString)
        val expectedMovieList = mockMovieData.toDomainMovieList() // Use the extension function to convert to domain model

        // Mock API response
        coEvery { apiService.getMovies(BuildConfig.API_KEY) } returns Response.success(mockMovieData)

        // Execute the method under test
        val result = movieDataSource.getMovies()

        // Assert that the result contains the expected success value
        when (val successResult = result) {
            is com.example.domain.usecase.Response.Success -> assertEquals(expectedMovieList, successResult.data) // Validate success content
            else -> fail(FAILURE + successResult)
        }
    }

    @Test
    fun `getMovies handles HttpException`() = testScope.runTest {
        // Mock an HttpException
        coEvery { apiService.getMovies(BuildConfig.API_KEY) } throws HttpException(Response.error<Nothing>(404, mockk(relaxed = true)))

        // Execute the method under test
        val result = movieDataSource.getMovies()

        //Assert
        when (val errorResult = result) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains("HTTP")) // Validate failure content
            else -> fail(FAILURE + errorResult)
        }
    }

    @Test
    fun `getMovies handles IOException`() = testScope.runTest {
        // Mock an IOException
        coEvery { apiService.getMovies(BuildConfig.API_KEY) } throws IOException("Exception")

        // Execute the method under test
        val result = movieDataSource.getMovies()

        when (val errorResult = result) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains("IO"))
            else -> fail(FAILURE + errorResult)
        }
    }

    @Test
    fun `getMovieDetails returns success`() = testScope.runTest {
        val mockMovieDetailString = getJson(MOVIE_DETAILS_JSON_FILE)
        val mockMovieDetail = json.decodeFromString<MovieDetailDto>(mockMovieDetailString)
        val expectedMovieDetail = mockMovieDetail.toDomainMovieDetail()

        // Mock API response
        coEvery { apiService.getMovieDetails(MOVIE_ID, BuildConfig.API_KEY) } returns Response.success(mockMovieDetail)

        // Execute the method under test
        val result = movieDataSource.getMoviesDetails(MOVIE_ID)

        // Assert
        when (val successResult = result) {
            is com.example.domain.usecase.Response.Success -> assertEquals(expectedMovieDetail, successResult.data) // Validate success content
            else -> fail(FAILURE + successResult)
        }
    }

    @Test
    fun `getMovieDetails handles HttpException`() = testScope.runTest {
        // Mock an HttpException
        coEvery { apiService.getMovieDetails(MOVIE_ID, BuildConfig.API_KEY) } throws HttpException(Response.error<Nothing>(404, mockk(relaxed = true)))

        // Execute the method under test
        val result = movieDataSource.getMoviesDetails(MOVIE_ID)

        // Assert
        when (val errorResult = result) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains(HTTP_EXCEPTION)) // Validate failure message
            else -> fail(FAILURE + errorResult)
        }
    }

    @Test
    fun `getMovieDetails handles IOException`() = testScope.runTest {
        // Mock an IOException
        coEvery { apiService.getMovieDetails(MOVIE_ID, BuildConfig.API_KEY) } throws IOException(IO_EXCEPTION)

        // Execute the method under test
        val result = movieDataSource.getMoviesDetails(MOVIE_ID)

        // Assert
        when (val errorResult = result) {
            is com.example.domain.usecase.Response.Failure -> assertTrue(errorResult.message.contains(IO_EXCEPTION))
            else -> fail(FAILURE + errorResult)
        }
    }

    @After
    fun tearDown() {

    }

    private fun getJson(path: String): String {
        val resourceAsStream = this::class.java.classLoader?.getResourceAsStream(path)
        return resourceAsStream?.bufferedReader().use { it?.readText() } ?: ""
    }

    companion object {
        private const val MOVIE_ID = 1011985
        private const val MOVIES_JSON_FILE = "mocked_movies.json"
        private const val MOVIE_DETAILS_JSON_FILE = "mocked_movie_details.json"
        private const val FAILURE = "FAILURE"
        private const val IO_EXCEPTION = "IO error"
        private const val HTTP_EXCEPTION = "HTTP error"
    }
}