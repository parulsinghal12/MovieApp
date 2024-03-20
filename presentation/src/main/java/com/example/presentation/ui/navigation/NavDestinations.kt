package com.example.presentation.ui.navigation

sealed class NavDestinations(val destination: String) {

    object MOVIE_LIST_SCREEN_DESTINATION : NavDestinations (NAV_TO_MOVIE_LIST_SCREEN)
    object MOVIE_DETAIL_SCREEN_DESTINATION : NavDestinations (NAV_TO_MOVIE_DETAIL_SCREEN)

    companion object {
        private const val NAV_TO_MOVIE_LIST_SCREEN = "NAV_TO_MOVIE_LIST_SCREEN"
        private const val NAV_TO_MOVIE_DETAIL_SCREEN = "NAV_TO_MOVIE_DETAIL_SCREEN/{movieId}"

        fun createMovieDetailRoute(movieId: Int, title: String) = "NAV_TO_MOVIE_DETAIL_SCREEN/$movieId/$title"
    }
}