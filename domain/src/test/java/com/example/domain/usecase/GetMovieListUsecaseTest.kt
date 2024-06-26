package com.example.domain.usecase


import com.example.domain.MockData
import com.example.domain.model.MovieList
import com.example.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetMovieListUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMovieListUseCase: GetMovieListUsecase

    @Before
    fun setUp() {
        movieRepository = mockk()
        getMovieListUseCase = GetMovieListUsecase(movieRepository)
    }

    @Test
    fun `invoke returns successful response`() = runTest {
        // Mock response
        val mockMovieList: MovieList = MockData.mockMovieList
        val successResponse = Response.Success(mockMovieList)

        // Mock the repository's behavior
        coEvery { movieRepository.getMovies() } returns (successResponse)

        //collect results emitted by flow to list
        val results = getMovieListUseCase()

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
        coEvery { movieRepository.getMovies() } returns (errorResponse)

        // Invoke the use case and collect the response
        val response = getMovieListUseCase()

        // Verify the response is the error response
        assertEquals(errorResponse, response)
    }

    companion object {
        private const val ERROR_MSG = "An error occurred"
    }
}
