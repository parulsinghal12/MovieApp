package com.example.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.R
import com.example.presentation.movieList.screen.MoviesScreen

@Composable
fun NavGraph (navController: NavHostController = rememberNavController(),
              toolBarTitle: MutableState<String>,
              secondaryScreenHeader: MutableState<Boolean>) {

    NavHost(navController, startDestination = NavDestinations.MOVIE_LIST_SCREEN_DESTINATION.destination) {
        composable(route = NavDestinations.MOVIE_LIST_SCREEN_DESTINATION.destination) {
            toolBarTitle.value = stringResource(id = R.string.app_name)
            secondaryScreenHeader.value = false
            MoviesScreen(
                selectedMovie = { movieId: Int, title: String ->
                    navController.navigate(NavDestinations.createMovieDetailRoute(movieId,title))
                }
            )
        }
    }
}