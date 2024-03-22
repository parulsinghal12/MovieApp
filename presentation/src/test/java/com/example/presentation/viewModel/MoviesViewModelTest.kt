package com.example.presentation.viewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.MovieList
import com.example.domain.usecase.GetMovieListUsecase
import com.example.domain.usecase.Response
import com.example.presentation.MockData
import com.example.presentation.mapper.toMovieListUiModel
import com.example.presentation.movieList.contract.MovieListContract
import com.example.presentation.movieList.viewModel.MoviesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule() // instant live updates

    private val getMovieListUsecase: GetMovieListUsecase = mockk(relaxed = true)
    private lateinit var moviesViewModel: MoviesViewModel

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        testScope = TestScope(testDispatcher)
        moviesViewModel = MoviesViewModel(getMovieListUsecase)
    }

    @Test
    fun `getMoviesList emits Success state when useCase returns success`() = testScope.runTest {
        //Mock data
        val mockMovieList: MovieList = MockData.mockMovieList
        val successResponse = Response.Success(mockMovieList)

        //expected result
        val expectedState = MovieListContract.ViewState.Success(mockMovieList.toMovieListUiModel())

        coEvery { getMovieListUsecase() } returns flowOf(successResponse)

        moviesViewModel.sendEvent(MovieListContract.ViewIntent.GetMoviesList)

        assertEquals(expectedState, moviesViewModel.viewState.value)
    }

    @Test
    fun `getMoviesList emits Error state when useCase returns failure`() = testScope.runTest {
        // The coroutine context for this test will use UnconfinedTestDispatcher
        val errorMessage = "An error occurred"
        val errorResponse = Response.Failure(errorMessage)

        coEvery { getMovieListUsecase() } returns flowOf(errorResponse)

        moviesViewModel.sendEvent(MovieListContract.ViewIntent.GetMoviesList)

        val expectedState = MovieListContract.ViewState.Error(errorMessage)
        assertEquals(expectedState, moviesViewModel.viewState.value)
    }

    @Test
    fun `sendEvent OnMovieItemClicked emits NavigateToDetailsScreen side effect`() = testScope.runTest {
        val movieId = 123 // Example movie ID

        // Prepare a deferred side effect collection
        val sideEffectDeferred = async {
            moviesViewModel.sideEffect.first { it is MovieListContract.SideEffect.NavigateToDetailsScreen }
        }

        // Trigger the event
        moviesViewModel.sendEvent(MovieListContract.ViewIntent.OnMovieItemClicked(movieId))

        // Await the side effect and perform the assertion
        val sideEffect = sideEffectDeferred.await()
        assertTrue(sideEffect is MovieListContract.SideEffect.NavigateToDetailsScreen && sideEffect.movieId == movieId)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }
}