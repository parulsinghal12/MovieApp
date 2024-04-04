package com.example.presentation.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.collectAsState
import com.example.domain.model.MovieList
import com.example.domain.usecase.GetMovieListUsecase
import com.example.domain.usecase.Response
import com.example.presentation.MockData
import com.example.presentation.mapper.toMovieListUiModel
import com.example.presentation.movieList.contract.MovieListContract
import com.example.presentation.movieList.viewModel.MoviesViewModel
import io.mockk.clearAllMocks
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() // instant live updates

    private val getMovieListUsecase: GetMovieListUsecase = mockk(relaxed = true)
    private lateinit var moviesViewModel: MoviesViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        moviesViewModel = MoviesViewModel(getMovieListUsecase)
    }

    @Test
    fun `getMoviesList emits Success state when useCase returns success`() = runTest {
        // Mock data
        val mockMovieList = MockData.mockMovieList // Assuming you have some mock data
        val successResponse = Response.Success(mockMovieList)
        coEvery { getMovieListUsecase.invoke() } returns successResponse
        val viewModel = MoviesViewModel(getMovieListUsecase)
        //Test
        viewModel.sendEvent(MovieListContract.ViewIntent.GetMoviesList)
        val state = viewModel.viewState.first()

        // Assert that the emitted state is Success and contains the expected data
        assertTrue(state is MovieListContract.ViewState.Success)
        assertEquals(mockMovieList.toMovieListUiModel(), (state as MovieListContract.ViewState.Success).data)
    }

    @Test
    fun `getMoviesList emits Error state when useCase returns failure`() = runTest {
        val errorResponse = Response.Failure(ERROR_MESSAGE)
        coEvery { getMovieListUsecase() } returns (errorResponse)
        moviesViewModel.sendEvent(MovieListContract.ViewIntent.GetMoviesList)
        // Wait for any asynchronous operations to complete
        advanceUntilIdle()
        // Assert the expected success state
        assert(moviesViewModel.viewState.value is MovieListContract.ViewState.Error)
        val failureState = moviesViewModel.viewState.value as MovieListContract.ViewState.Error
        assertEquals(errorResponse.message, failureState.message)
    }

    @Test
    fun `sendEvent OnMovieItemClicked emits NavigateToDetailsScreen side effect`() = runTest {
        val movieId = MOVIE_ID
        val sideEffects = mutableListOf<MovieListContract.SideEffect>()
        val job = launch(testDispatcher) {
            moviesViewModel.sideEffect.toList(sideEffects)
        }
        moviesViewModel.sendEvent(MovieListContract.ViewIntent.OnMovieItemClicked(MOVIE_ID))
        advanceUntilIdle()

        assertTrue(sideEffects.any { it is MovieListContract.SideEffect.NavigateToDetailsScreen && it.movieId == movieId })

        job.cancel()
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    companion object {
        // constants
        const val ERROR_MESSAGE = "An error occurred"
        const val MOVIE_ID = 123
    }
}