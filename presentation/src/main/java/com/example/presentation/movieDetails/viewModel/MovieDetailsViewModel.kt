package com.example.presentation.movieDetails.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetMovieDetailUsecase
import com.example.domain.usecase.Response
import com.example.presentation.contract.NoOpSideEffect
import com.example.presentation.mapper.toMovieDetailUiModel
import com.example.presentation.movieDetails.contract.MovieDetailContract

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    val getMovieDetailUsecase: GetMovieDetailUsecase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), MovieDetailContract {

    private val _state = MutableStateFlow(value = loadingState())

    override val viewState: StateFlow<MovieDetailContract.ViewState>
        get() = _state.asStateFlow()

    //no side effect in this screen hence initializing with MutableSharedFlow.
    // Without emitting any items it will serve the purpose of having an "empty" SharedFlow
    override val sideEffect: SharedFlow<NoOpSideEffect> = MutableSharedFlow()

    init {
        val movieId = savedStateHandle.get<Int>("movieId") ?: throw IllegalArgumentException("Movie ID not found")
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
        viewModelScope.launch(Dispatchers.IO) {

            val response = getMovieDetailUsecase(movieId)
            when (response) {
                is Response.Failure ->
                    _state.value = MovieDetailContract.ViewState.Error(response.message)

                is Response.Success -> {
                    val temp = response.data.toMovieDetailUiModel()
                    println("temp = ${temp.toString()}")
                    println("temp2 = ${MovieDetailContract.ViewState.Success(temp)}")
                    _state.value =
                        MovieDetailContract.ViewState.Success(temp)
                }
            }

        }
    }

}