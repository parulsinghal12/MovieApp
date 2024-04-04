package com.example.presentation.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.domain.model.MovieDetail
import com.example.domain.usecase.GetMovieDetailUsecase
import com.example.domain.usecase.Response
import com.example.presentation.DispatcherRule
import com.example.presentation.MockData.mockMovieDetail
import com.example.presentation.contract.NoOpSideEffect
import com.example.presentation.movieDetails.contract.MovieDetailContract
import com.example.presentation.movieDetails.viewModel.MovieDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private var getMovieDetailUsecase = mockk<GetMovieDetailUsecase>(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle(mapOf("movieId" to MOVIE_ID))
        viewModel = MovieDetailsViewModel(getMovieDetailUsecase, savedStateHandle)
    }

    @Test
    fun `getMovieDetail emits Success state when useCase returns success`() = runTest {
        val mockMovieDetail: MovieDetail = mockMovieDetail
        val successResponse = Response.Success(mockMovieDetail)
        coEvery { getMovieDetailUsecase.invoke(MOVIE_ID) } returns successResponse

        viewModel.sendEvent(MovieDetailContract.ViewIntent.GetMovieDetails(MOVIE_ID))

        viewModel.viewState.test {
            Assert.assertTrue(awaitItem() is MovieDetailContract.ViewState.Success)
        }
    }

    @Test
    fun `getMovieDetail emits Error state when useCase returns failure`() = runTest {
        // Mock behavior of the use case to return failure
        coEvery { getMovieDetailUsecase.invoke(MOVIE_ID) } returns Response.Failure(ERROR_MESSAGE)

        // Test
        viewModel.sendEvent(MovieDetailContract.ViewIntent.GetMovieDetails(MOVIE_ID))
        advanceUntilIdle()
        // Ensure the correct state is emitted
        assertTrue(viewModel.viewState.value is MovieDetailContract.ViewState.Error)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val loadingState = viewModel.loadingState()
        assertTrue(loadingState is MovieDetailContract.ViewState.Loading )
    }

    @Test
    fun `sideEffect stream is empty`() = runTest {
        val sideEffectValues = mutableListOf<NoOpSideEffect>()

        val job = launch { viewModel.sideEffect.collect { sideEffectValues.add(it) } }

        viewModel.sendEvent(MovieDetailContract.ViewIntent.GetMovieDetails(MOVIE_ID))
        advanceUntilIdle()
        assert(sideEffectValues.isEmpty())

        job.cancel()
    }

    companion object {
        // constants
        const val ERROR_MESSAGE = "Error fetching movie details"
        const val MOVIE_ID = 1011985
    }
}
