package com.example.albumapp.ui.screens


import androidx.compose.foundation.Image
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
    onShowButtonClicked: (Photo) -> Unit,
    onSaveButtonClicked: (Photo) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (albumUiState) {
        is AlbumUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlbumUiState.Success -> PhotosGridScreen(
            albumUiState.photos,
            onShowButtonClicked = onShowButtonClicked,
            onSaveButtonClicked = onSaveButtonClicked,
            contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
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
    onShowButtonClicked: (Photo) -> Unit,
    onSaveButtonClicked: (Photo) -> Unit,
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
                onShowButtonClicked = onShowButtonClicked,
                onSaveButtonClicked = onSaveButtonClicked,
                modifier = modifier
//                    .padding(4.dp)
                    .fillMaxWidth()
//                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun MarsPhotoCard(
    photo: Photo,
    onShowButtonClicked: (Photo) -> Unit,
    onSaveButtonClicked: (Photo) -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
//                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(150.dp)
//                    .clickable { /* Handling for å vise bilde */ }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = photo.title,
                    //modifier = Modifier.padding(8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ShowPhotoButton(onClick = { onShowButtonClicked(photo) })
//                    DeletePhotoButton(onClick = { /* Slett bilde */ })
                    SavePhotoButton(onClick = { onSaveButtonClicked(photo) })
                }
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

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    AlbumAppTheme {
        val mockData = List(15) { Photo(it, it,"title_test","url", "imgSrc") }
        PhotosGridScreen(mockData, onSaveButtonClicked = {}, onShowButtonClicked = {})
    }
}

