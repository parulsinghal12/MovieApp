package com.example.presentation.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.MovieDetail
import com.example.domain.usecase.GetMovieDetailUsecase
import com.example.domain.usecase.Response
import com.example.presentation.mapper.toMovieDetailUiModel
import com.example.presentation.movieDetails.contract.MovieDetailContract
import com.example.presentation.movieDetails.viewModel.MovieDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getMovieDetailUsecase: GetMovieDetailUsecase = mockk()
    private lateinit var viewModel: MovieDetailsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieDetailsViewModel(getMovieDetailUsecase)
    }

    @Test
    fun `getMovieDetail emits Success state when useCase returns success`() = runTest {
        val mockMovieDetail: MovieDetail = mockk(relaxed = true) // Prepare mock MovieDetail
        val successResponse = Response.Success(mockMovieDetail)

        coEvery { getMovieDetailUsecase(any()) } returns flowOf(successResponse)

        viewModel.sendEvent(MovieDetailContract.ViewIntent.GetMovieDetails(123))

        val expectedState = MovieDetailContract.ViewState.Success(mockMovieDetail.toMovieDetailUiModel())
        assertEquals(expectedState, viewModel.viewState.value)
    }

    @Test
    fun `getMovieDetail emits Error state when useCase returns failure`() = runTest {
        val errorMessage = "Error fetching movie details"
        val errorResponse = Response.Failure(errorMessage)

        // Mock the use case's behavior
        coEvery { getMovieDetailUsecase(any()) } returns flowOf(errorResponse)

        viewModel.sendEvent(MovieDetailContract.ViewIntent.GetMovieDetails(123))

        // Assert that the view state is updated to Error with the correct message
        val expectedState = MovieDetailContract.ViewState.Error(errorMessage)
        assertEquals(expectedState, viewModel.viewState.value)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        assertEquals(MovieDetailContract.ViewState.Loading, viewModel.viewState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
