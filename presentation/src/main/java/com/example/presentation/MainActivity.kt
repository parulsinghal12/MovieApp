package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.presentation.ui.components.MainAppBar
import com.example.presentation.ui.navigation.NavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val toolBarTitle = remember { mutableStateOf(getString(R.string.app_name)) }
            val secondaryScreenHeader = remember { mutableStateOf(false) }
            val navController = rememberNavController()
            MoviesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            MainAppBar(title = toolBarTitle.value, showSecondaryHeader = secondaryScreenHeader.value, navController)
                        }
                    ) { innerPadding ->
                        // Apply the padding to the content
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavGraph(
                                navController = navController,
                                toolBarTitle = toolBarTitle,
                                secondaryScreenHeader = secondaryScreenHeader
                            )
                        }
                    }
                }
            }
        }
    }
}