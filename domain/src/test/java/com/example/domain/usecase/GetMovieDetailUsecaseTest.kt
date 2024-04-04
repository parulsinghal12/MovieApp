package com.example.domain.usecase

import com.example.domain.MockData
import com.example.domain.model.MovieDetail
import com.example.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieDetailUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieDetailUsecase: GetMovieDetailUsecase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getMovieDetailUsecase = GetMovieDetailUsecase(movieRepository)
    }

    @Test
    fun `invoke returns successful response`() = runTest {
        // Mock response
        val mockMovieDetail: MovieDetail = MockData.mockMovieDetail
        val successResponse = Response.Success(mockMovieDetail)

        // Mock the repository's behavior
        coEvery { movieRepository.getMovieDetails(MOVIE_ID) } returns (successResponse)

        //collect results emitted by flow to list
        val results = getMovieDetailUsecase(MOVIE_ID)
        //assertEquals(1, results.size) // Ensure there's exactly one emission

        //fetch the first emission (though only one would be there)
        val response = results
        when (response) {
            is Response.Failure -> fail(ERROR_MSG)
            is Response.Success -> assertEquals(successResponse.data, response.data)
            // Validate success content
        }
    }

    @Test
    fun `invoke returns error response`() = runTest {
        // Prepare a mock error response
        val errorResponse = Response.Failure(ERROR_MSG)

        // Mock the repository's behavior to return an error
        coEvery { movieRepository.getMovieDetails(MOVIE_ID) } returns (errorResponse)

        // Invoke the use case and collect the response
        val response = getMovieDetailUsecase(MOVIE_ID)

        // Verify the response is the error response
        assertEquals(errorResponse, response)
    }

    companion object {
        private const val MOVIE_ID = 123
        private const val ERROR_MSG = "An error occurred"
    }
}
