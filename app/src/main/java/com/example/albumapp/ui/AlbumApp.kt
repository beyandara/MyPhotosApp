package com.example.albumapp.ui


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.albumapp.R
import com.example.albumapp.ui.screens.AlbumScreen
import com.example.albumapp.ui.screens.AlbumViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { MarsTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val albumViewModel: AlbumViewModel =
                viewModel(factory = AlbumViewModel.Factory)
            AlbumScreen(
                albumUiState = albumViewModel.albumUiState,
                retryAction = albumViewModel::getAlbum,
                contentPadding = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}



/**
enum class AlbumScreens {
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun PhotoAlbumApp(
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
                    albumUiState = albumViewModel.albumUiState,
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
} */