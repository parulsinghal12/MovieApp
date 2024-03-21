package com.example.presentation.movieDetails.contract

import com.example.presentation.contract.MVIContract
import com.example.presentation.model.MovieListUiModel
import com.example.presentation.movieList.contract.MovieListContract

interface MovieDetailContract: MVIContract<MovieDetailContract.ViewState, MovieDetailContract.ViewIntent, MovieDetailContract.SideEffect> {
    sealed interface ViewState {
        object Loading : ViewState
        data class Success(val data: MovieListUiModel) : ViewState
        data class Error(val message: String) : ViewState
    }

    sealed interface ViewIntent {
        object GetMovieDetails : ViewIntent
    }

    sealed interface SideEffect {

    }
}