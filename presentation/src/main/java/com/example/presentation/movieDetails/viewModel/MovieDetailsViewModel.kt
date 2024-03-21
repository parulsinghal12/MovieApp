package com.example.presentation.movieDetails.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetMovieDetailUsecase
import com.example.presentation.movieDetails.contract.MovieDetailContract

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(val getMovieDetailUsecase: GetMovieDetailUsecase) : ViewModel(),
    MovieDetailContract {

    private val _state = MutableStateFlow(value = createInitialState())

    override fun sendEvent(viewIntent: MovieDetailContract.ViewIntent) {
        TODO("Not yet implemented")
    }

    override fun createInitialState(): MovieDetailContract.ViewState {
        TODO("Not yet implemented")
    }

    override val viewState: StateFlow<MovieDetailContract.ViewState>
        get() = TODO("Not yet implemented")
    override val sideEffect: SharedFlow<MovieDetailContract.SideEffect>
        get() = TODO("Not yet implemented")


}