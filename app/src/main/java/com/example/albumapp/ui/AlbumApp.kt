package com.example.albumapp.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.albumapp.ui.screens.AlbumScreen
import com.example.albumapp.ui.screens.AlbumViewModel
import com.example.albumapp.ui.screens.SelectedPhotoScreen
import com.example.myphotosapp.R

enum class AlbumScreens() {
    Start,
    Selected
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun PhotoAlbumApp(
    viewModel: AlbumViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            AlbumAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }
    ) { innerPadding ->
//        val uiState by viewModel.uiState.collectAsState()
        val albumViewModel: AlbumViewModel =
            viewModel(factory = AlbumViewModel.Factory)


        NavHost(
            navController = navController,
            startDestination = AlbumScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AlbumScreens.Start.name) {
                AlbumScreen(
                    albumUiState = albumViewModel.amphibiansUiState,
                    onShowButtonClicked = {},
                    onSaveButtonClicked = {},
                    onDeleteButtonClicked = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = AlbumScreens.Selected.name) {
                SelectedPhotoScreen(
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}