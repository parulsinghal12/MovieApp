package com.example.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(title: String, showSecondaryHeader: Boolean, navController: NavHostController) {
    Column {
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                if (showSecondaryHeader) {
                    IconButton(onClick =  { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.DarkGray
                        )
                    }
                }
            }
        )
    }
}