package com.example.albumapp.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.albumapp.R
import com.example.albumapp.model.Photo
import com.example.albumapp.ui.theme.AlbumAppTheme

@Composable
fun AlbumScreen(
    albumUiState: AlbumUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (albumUiState) {
        is AlbumUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlbumUiState.Success -> PhotosGridScreen(
            albumUiState.photos, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
        )
        is AlbumUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_broken_image), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("retry")
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
fun PhotosGridScreen(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(
            items = photos,
            key = { photo ->
                photo.id
            }
        ) { photo ->
            MarsPhotoCard(
                photo = photo,
                modifier = modifier
//                    .padding(4.dp)
                    .fillMaxWidth()
//                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun MarsPhotoCard(photo: Photo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.imgSrc)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(150.dp)
                    .clickable { /* Handling for å vise bilde */ }
            )
            Text(
                text = photo.title,
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ShowPhotoButton(onClick = { /* Vis bilde */ })
                DeletePhotoButton(onClick = { /* Slett bilde */ })
            }
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
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AlbumAppTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AlbumAppTheme {
        ErrorScreen({})
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PhotosGridScreenPreview() {
//    MarsPhotosTheme {
//        val mockData = List(15) { MarsPhoto(it, it,"title_test","url", "imgSrc") }
//        PhotosGridScreen(mockData)
//    }
//}



/**
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
import com.example.albumapp.R
import com.example.albumapp.model.Photo
import com.example.albumapp.ui.theme.AlbumAppTheme

@Composable
fun al(
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
//                SavedPhotoCard()
            }
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

@Composable
fun AlbumScreen(
    albumUiState: AlbumUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (albumUiState) {
        is AlbumUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is AlbumUiState.Success ->
            AlbumListScreen(
                album = albumUiState.album,
                modifier = modifier,
                contentPadding = contentPadding
            )
        else -> ErrorScreen(modifier)
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
fun ListScreenPreview() {
    AlbumAppTheme {
        val mockData = List(10) {
            Photo(
                it,
                it,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do",
                "imgSrc",
                thumbnailUrl = "www.test.no"
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
                "imgSrc",
                thumbnailUrl = "www.test.no"
            )
        AvailablePhotoCard(photo = photo)
    }
}

//
@Preview
@Composable
fun HomeScreenPreview() {
    AlbumAppTheme {
        val albumViewModel: AlbumViewModel =
            viewModel(factory = AlbumViewModel.Factory)
        AlbumScreen(
            albumUiState = albumViewModel.albumUiState,
            modifier = Modifier.fillMaxSize()
        )
    }
}*/