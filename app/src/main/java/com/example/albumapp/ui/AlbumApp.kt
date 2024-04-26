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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

//ITS WORKING!! ITS WORKING!!
enum class AlbumScreens(@StringRes val title:Int) {
    Start(title = R.string.title),
    SelectedPhoto(title = R.string.Album_title)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumTopAppBar(
    currentScreen: AlbumScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
//                text = stringResource(R.string.app_name),
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
                    painter = painterResource(R.drawable.artshopicon),   // TODO MÅ HA ET EGET IKON
                    contentDescription = null,
                    modifier = modifier
                        .size(40.dp)
                        .padding(4.dp)
                )
            }
        }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AlbumScreens.valueOf(
        backStackEntry?.destination?.route?: AlbumScreens.Start.name

    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val viewModel: AlbumViewModel =
        viewModel(factory = AlbumViewModel.Factory)

    val savedItemsState = viewModel.savedItems.collectAsState(initial = emptyList())


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AlbumTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp()},
                scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
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
                        savedItems = savedItemsState.value,
                        onShowButtonClicked = {selectedPhoto ->
                            viewModel.setSelectedPhoto(selectedPhoto)
                            navController.navigate(AlbumScreens.SelectedPhoto.name) },
                        onSaveButtonClicked = {},
                        onDeleteButtonClicked = {},
                        retryAction = viewModel::getAlbum,
                    )
                }

                /** VIEW SELECTED PHOTO */

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


