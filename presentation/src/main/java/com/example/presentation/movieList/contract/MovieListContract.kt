package com.example.presentation.movieList.contract

import com.example.presentation.contract.MVIContract
import com.example.presentation.model.MovieListUiModel

interface MovieListContract : MVIContract<MovieListContract.ViewState, MovieListContract.ViewIntent, MovieListContract.SideEffect> {
    sealed interface ViewState {
        object Loading : ViewState
        data class Success(val data: MovieListUiModel) : ViewState
        data class Error(val message: String) : ViewState
    }

    sealed interface ViewIntent {
        object GetMoviesList : ViewIntent
        data class OnMovieItemClicked(val movieId: Int, val title: String) : ViewIntent
    }

    sealed interface SideEffect {
        data class NavigateToDetailsScreen(val movieId: Int, val title: String) : SideEffect
    }
}

