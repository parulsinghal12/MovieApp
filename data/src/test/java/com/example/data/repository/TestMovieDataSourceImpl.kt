package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.api.ApiService
import com.example.data.mapper.toDomainMovieList
import com.example.data.model.MovieListDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class TestMovieDataSourceImpl {

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

        // Collecting
        val result = movieDataSource.getMovies().toList()

        // Assert that the result contains the expected success value
        assert(result.size == 1) // Ensure there's exactly one emission
        when (val successResult = result.first()) {
            is com.example.domain.usecase.Response.Success -> assertEquals(expectedMovieList, successResult.data) // Validate success content
            else -> fail("Expected Success, got $successResult")
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