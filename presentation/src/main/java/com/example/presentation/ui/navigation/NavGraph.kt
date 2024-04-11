package com.example.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.R
import com.example.presentation.movieDetails.screen.MovieDetailsScreen
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
                selectedMovie = { movieId: Int ->
                    navController.navigate(NavDestinations.createMovieDetailRoute(movieId))
                }
            )
        }

        composable(route = NavDestinations.MOVIE_DETAIL_SCREEN_DESTINATION.destination,
            arguments = listOf(
                navArgument(NavigationArgs.MOVIE_ID) { type = NavType.IntType }
            )) {
            toolBarTitle.value = stringResource(id = R.string.app_name)
            secondaryScreenHeader.value = true
            MovieDetailsScreen(/*movieID =  it.arguments?.getInt("movieId") ?: 0*/)
        }
    }
}