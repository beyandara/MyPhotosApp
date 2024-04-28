package com.example.albumapp.ui


import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.albumapp.R
import com.example.albumapp.ui.screens.AlbumScreen
import com.example.albumapp.ui.screens.AlbumViewModel
import com.example.albumapp.ui.screens.SelectedPhotoScreen
import kotlinx.coroutines.launch

enum class AlbumScreens(@StringRes val title:Int) {
    Start(title = R.string.my_pictures),
    SelectedPhoto(title = R.string.chosen_photo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopAppBar(
    currentScreen: AlbumScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(currentScreen.title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            } else {
                Icon(
                    painter = painterResource(R.drawable.photoalbumicon),
                    contentDescription = null,
                    modifier = modifier
                        .size(50.dp)
                        .padding(4.dp)
                )
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlbumApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AlbumScreens.valueOf(
        backStackEntry?.destination?.route?: AlbumScreens.Start.name

    )
    val viewModel: AlbumViewModel =
        viewModel(factory = AlbumViewModel.Factory)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AlbumTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp()},
            )}
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(top=100.dp)
        ) {
            NavHost(
                navController = navController,
                startDestination = AlbumScreens.Start.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
            ) {

                composable(route = AlbumScreens.Start.name) {
                    AlbumScreen(
                        albumUiState = viewModel.albumUiState,
                        onShowButtonClicked = {selectedPhoto ->
                            viewModel.setSelectedPhoto(selectedPhoto)
                            navController.navigate(AlbumScreens.SelectedPhoto.name) },
                        onSaveButtonClicked = {selectedPhoto ->
                            viewModel.setSelectedPhoto(selectedPhoto)
                            coroutineScope.launch {
                            viewModel.saveItem(selectedPhoto)
                            } },
                        onDeleteButtonClicked = {selectedPhoto ->
                            viewModel.setSelectedPhoto(selectedPhoto)
                            coroutineScope.launch {
                                viewModel.deleteItem(selectedPhoto)
                            }
                        },
                        retryAction = viewModel::getAlbum,
                        viewModel = viewModel
                    )
                }

                composable(route = AlbumScreens.SelectedPhoto.name) {
                    val selectedPhoto = viewModel.selectedPhoto
                    SelectedPhotoScreen(
                        photo = selectedPhoto
                    )
                }
            }
        }
    }
}


