package com.example.albumapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.albumapp.model.Photo
import com.example.albumapp.ui.theme.AlbumAppTheme
import com.example.albumapp.R

@Composable
fun AlbumScreen(
    albumUiState: AlbumUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .padding(4.dp)
                .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp))
        ) {
            Column {
                Text(text = stringResource(R.string.saved_photos))
                SavedPhotoCard()
            }
        }
        Box(
            modifier = modifier
                .padding(4.dp)
                .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp))
        ) {
            Column {
                Text(text = "jsonplaceholder")
                when (albumUiState) {
                    is AlbumUiState.Loading -> LoadingScreen()
                    is AlbumUiState.Success -> {
                        AlbumListScreen(
                            album = albumUiState.album,
                            modifier = modifier
                        )
                    }
                    is AlbumUiState.Error -> ErrorScreen()
                }
            }
        }
    }
}


@Composable
fun AvailablePhotoCard(photo: Photo,
                       modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.imgSrc)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
            Column {
                Text(
                    text = photo.title)
//                Row {
//                    ShowPhotoButton(onClick = { /*TODO*/ })
//                    SavePhotoButton(onClick = { /*TODO*/ })
//                }

            }

        }
    }
}

@Composable
private fun AlbumListScreen(
    album: List<Photo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = album,
            key = { photo ->
                photo.title
            }
        ){photo ->
            AvailablePhotoCard(photo = photo, modifier = modifier.fillMaxSize())
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
//        Button(onClick = retryAction) {
//            Text(stringResource(R.string.retry))
//        }
    }
}


@Composable
fun SavedPhotoCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null
            )
//            ShowPhotoButton(onClick = { /*TODO*/ })
//            DeletePhotoButton(onClick = { /*TODO*/ })
        }
    }
}



@Composable
fun ShowPhotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(stringResource(R.string.show))
    }
}

@Composable
fun DeletePhotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(stringResource(R.string.delete))
    }
}

@Composable
fun SavePhotoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(stringResource(R.string.save))
    }
}


@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AlbumAppTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()
                .size(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AlbumAppTheme {
        ErrorScreen(Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun AmphibiansListScreenPreview() {
    AlbumAppTheme {
        val mockData = List(10) {
            Photo(
                it,
                it,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do",
                "www.test.no",
                imgSrc = ""
            )
        }
        AlbumListScreen(mockData, Modifier.fillMaxSize())
    }
}

@Preview
@Composable
fun AvailablePhotoCardPreview() {
    AlbumAppTheme {


        val photo =
            Photo(
                1,
                2,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do",
                "www.test.no",
                imgSrc = ""
            )
        AvailablePhotoCard(photo = photo)
    }
}

//
@Preview
@Composable
fun StartOrderPreview() {
        AlbumAppTheme {

            val albumViewModel: AlbumViewModel =
                viewModel(factory = AlbumViewModel.Factory)


            AlbumScreen(
                albumUiState = albumViewModel.albumUiState,
//                onShowButtonClicked = {},
//                onSaveButtonClicked = {},
//                onDeleteButtonClicked = {},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
