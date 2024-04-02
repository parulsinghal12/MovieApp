package com.example.presentation.movieDetails.contract

import com.example.presentation.contract.MVIContract
import com.example.presentation.contract.NoOpSideEffect
import com.example.presentation.model.MovieDetailUiModel

interface MovieDetailContract: MVIContract<MovieDetailContract.ViewState, MovieDetailContract.ViewIntent, NoOpSideEffect> {
    sealed interface ViewState {
        object Loading : ViewState
        data class Success(val data: MovieDetailUiModel) : ViewState
        data class Error(val message: String) : ViewState
    }

    sealed interface ViewIntent {
        data class GetMovieDetails(val movieId: Int) : ViewIntent
    }

}