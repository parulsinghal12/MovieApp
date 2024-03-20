package com.example.presentation.movieList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetMovieListUsecase
import com.example.domain.usecase.Response
import com.example.presentation.contract.MVIContract
import com.example.presentation.mapper.toMovieListUiModel
import com.example.presentation.movieList.contract.MovieListContract
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
class MoviesViewModel @Inject constructor(val getMovieListUsecase: GetMovieListUsecase) : ViewModel(), MovieListContract {


    private val _state = MutableStateFlow(value = createInitialState())

    override val viewState: StateFlow<MovieListContract.ViewState>
        get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MovieListContract.SideEffect>()
    override val sideEffect: SharedFlow<MovieListContract.SideEffect>
        get() = _sideEffect.asSharedFlow()

    override fun sendEvent(viewIntent: MovieListContract.ViewIntent) {
        when (viewIntent) {
            is MovieListContract.ViewIntent.GetMoviesList -> getMoviesList()
            is MovieListContract.ViewIntent.OnMovieItemClicked -> {
                viewModelScope.launch {
                    _sideEffect.emit(
                        MovieListContract.SideEffect.NavigateToDetailsScreen(
                            viewIntent.movieId,
                            viewIntent.title
                        )
                    )
                }

            }
        }
    }

    override fun createInitialState(): MovieListContract.ViewState{
        return  MovieListContract.ViewState.Loading
    }

    private fun getMoviesList() {
        viewModelScope.launch {
            _state.value = MovieListContract.ViewState.Loading
            getMovieListUsecase().collect {
                when (it) {
                    is Response.Failure -> _state.value =
                        MovieListContract.ViewState.Error(it.message ?: "")

                    is Response.Success -> _state.value = MovieListContract.ViewState.Success(
                        it.data.toMovieListUiModel()
                    )
                }
            }
        }
    }


}