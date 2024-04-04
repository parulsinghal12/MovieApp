package com.example.presentation.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain.usecase.GetMovieListUsecase
import com.example.domain.usecase.Response
import com.example.presentation.DispatcherRule
import com.example.presentation.MockData
import com.example.presentation.mapper.toMovieListUiModel
import com.example.presentation.movieList.contract.MovieListContract
import com.example.presentation.movieList.viewModel.MoviesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() // instant live updates

    @get:Rule
    val dispatcherRule = DispatcherRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val getMovieListUsecase: GetMovieListUsecase = mockk(relaxed = true)
    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun setup() {
        moviesViewModel = MoviesViewModel(getMovieListUsecase)
    }

    @Test
    fun `getMoviesList emits Success state when useCase returns success`() = runTest(testDispatcher) {
        // Mock data
        val mockMovieList = MockData.mockMovieList
        val successResponse = Response.Success(mockMovieList)
        coEvery { getMovieListUsecase.invoke() } returns successResponse
        //Test
        moviesViewModel.sendEvent(MovieListContract.ViewIntent.GetMoviesList)

        moviesViewModel.viewState.test {
            assertTrue(awaitItem() is MovieListContract.ViewState.Success)
        }
        assertEquals(mockMovieList.toMovieListUiModel(), (moviesViewModel.viewState.value as MovieListContract.ViewState.Success).data)

    }

    @Test
    fun `getMoviesList emits Error state when useCase returns failure`() = runTest {
        val errorResponse = Response.Failure(ERROR_MESSAGE)
        coEvery { getMovieListUsecase.invoke() } returns (errorResponse)
        moviesViewModel.sendEvent(MovieListContract.ViewIntent.GetMoviesList)

        moviesViewModel.viewState.test {
            assertTrue(awaitItem() is MovieListContract.ViewState.Error)
        }
        val failureState = moviesViewModel.viewState.value as MovieListContract.ViewState.Error
        assertEquals(errorResponse.message, failureState.message)
    }

    @Test
    fun `sendEvent OnMovieItemClicked emits NavigateToDetailsScreen side effect`() = runTest {
        //Note : Flow Collection Starts First: collecting from moviesViewModel.sideEffect before sending the event
        moviesViewModel.sideEffect.test {
            //Test
            moviesViewModel.sendEvent(MovieListContract.ViewIntent.OnMovieItemClicked(MOVIE_ID))
            val emittedItem = awaitItem()
            //Assert
            assertTrue(emittedItem is MovieListContract.SideEffect.NavigateToDetailsScreen && emittedItem.movieId == MOVIE_ID)
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        // constants
        const val ERROR_MESSAGE = "An error occurred"
        const val MOVIE_ID = 123
    }
}