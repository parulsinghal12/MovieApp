package com.example.presentation.movieDetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetMovieDetailUsecase
import com.example.domain.usecase.Response
import com.example.presentation.mapper.toMovieDetailUiModel
import com.example.presentation.movieDetails.contract.MovieDetailContract

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(val getMovieDetailUsecase: GetMovieDetailUsecase) : ViewModel(),
    MovieDetailContract {

    private val _state = MutableStateFlow(value = loadingState())

    override val viewState: StateFlow<MovieDetailContract.ViewState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MovieDetailContract.SideEffect>()
    override val sideEffect: SharedFlow<MovieDetailContract.SideEffect>
        get() = _sideEffect.asSharedFlow()


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
        viewModelScope.launch {
            _state.value = MovieDetailContract.ViewState.Loading
            val response = getMovieDetailUsecase(movieId)
            when (response) {
                is Response.Failure ->
                    _state.value = MovieDetailContract.ViewState.Error(response.message)

                is Response.Success ->
                    _state.value = MovieDetailContract.ViewState.Success(response.data.toMovieDetailUiModel())
            }

        }
    }

}