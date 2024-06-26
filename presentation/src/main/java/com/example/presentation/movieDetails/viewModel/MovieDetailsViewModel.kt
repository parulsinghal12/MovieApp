package com.example.presentation.movieDetails.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.di.IoDispatcher
import com.example.domain.usecase.GetMovieDetailUsecase
import com.example.domain.usecase.Response
import com.example.presentation.contract.NoOpSideEffect
import com.example.presentation.mapper.toMovieDetailUiModel
import com.example.presentation.movieDetails.contract.MovieDetailContract
import com.example.presentation.ui.navigation.NavigationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    val getMovieDetailUsecase: GetMovieDetailUsecase,
    private val savedStateHandle: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel(), MovieDetailContract {

    private val _state = MutableStateFlow(value = loadingState())

    override val viewState: StateFlow<MovieDetailContract.ViewState>
        get() = _state.asStateFlow()

    //no side effect in this screen hence initializing with MutableSharedFlow.
    // Without emitting any items it will serve the purpose of having an "empty" SharedFlow
    override val sideEffect: SharedFlow<NoOpSideEffect> = MutableSharedFlow()

    init {
        val movieId = savedStateHandle.get<Int>(NavigationArgs.MOVIE_ID) ?: throw IllegalArgumentException()
        sendEvent(MovieDetailContract.ViewIntent.GetMovieDetails(movieId))
    }

    override fun sendEvent(viewIntent: MovieDetailContract.ViewIntent) {
        when (viewIntent) {
            is MovieDetailContract.ViewIntent.GetMovieDetails ->
                getMovieDetail(viewIntent.movieId)
        }

    }
    override fun loadingState(): MovieDetailContract.ViewState {
        return  MovieDetailContract.ViewState.Loading
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch(ioDispatcher) {

            val response = getMovieDetailUsecase(movieId)
            when (response) {
                is Response.Failure ->
                    _state.value = MovieDetailContract.ViewState.Error(response.message)

                is Response.Success -> {
                    _state.value =
                        MovieDetailContract.ViewState.Success(response.data.toMovieDetailUiModel())
                }
            }

        }
    }

}