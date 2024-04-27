package com.example.albumapp.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    savedItems: List<Photo>,
    onShowButtonClicked: (Photo) -> Unit,
    onSaveButtonClicked: (Photo) -> Unit,
    onDeleteButtonClicked: (Photo) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val savedItemsState = remember { mutableStateOf(emptyList<Photo>()) }

    when (albumUiState) {
        is AlbumUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlbumUiState.Success -> AlbumScreenLayout(
            albumUiState.photos,
            savedItems = savedItemsState.value,
            onShowButtonClicked = onShowButtonClicked,
            onSaveButtonClicked = onSaveButtonClicked,
            onDeleteButtonClicked = onDeleteButtonClicked,
            contentPadding = contentPadding,
            modifier = modifier.fillMaxWidth()
        )
        is AlbumUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun AlbumScreenLayout(
    photos: List<Photo>,
    savedItems: List<Photo>,
    onShowButtonClicked: (Photo) -> Unit,
    onSaveButtonClicked: (Photo) -> Unit,
    onDeleteButtonClicked: (Photo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier
) {
    Column() {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            if (savedItems.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_saved_photos),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(contentPadding)
                )
            } else {
                PhotosGridScreen(
                    photos,
                    delete = false,
                    onShowButtonClicked = onShowButtonClicked,
                    onSaveOrDeleteButtonClicked = onSaveButtonClicked,
                    contentPadding = contentPadding,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }
        Divider(color = Color.Black, thickness = 3.dp)
        Column(
            modifier = Modifier.weight(1.5f)
        ) {
            PhotosGridScreen(
                photos,
                delete = false,
                onShowButtonClicked = onShowButtonClicked,
                onSaveOrDeleteButtonClicked = onSaveButtonClicked,
                contentPadding = contentPadding,
                modifier = modifier
                    .fillMaxWidth()
            )
        }
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
    delete: Boolean,
    onShowButtonClicked: (Photo) -> Unit,
    onSaveOrDeleteButtonClicked: (Photo) -> Unit,
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
            PhotoCard(
                photo = photo,
                delete = delete,
                onShowButtonClicked = onShowButtonClicked,
                onSaveOrDeleteButtonClicked = onSaveOrDeleteButtonClicked,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
//                    .aspectRatio(1.5f)
            )
        }
    }
}

/**
 * Display saved photos.
 */
@Composable
fun SavedPhotosGridScreen(
    itemList: List<Photo>,
    delete: Boolean,
    onSaveOrDeleteButtonClicked: (Photo) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(
            items = itemList, key = { it.id }
        ) { item ->
            ItemCard(
                item = item,
                delete = delete,
                onSaveOrDeleteButtonClicked = onSaveOrDeleteButtonClicked,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun PhotoCard(
    photo: Photo,
    delete: Boolean,
    onShowButtonClicked: (Photo) -> Unit,
    onSaveOrDeleteButtonClicked: (Photo) -> Unit,
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
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = photo.title,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    ShowPhotoButton(onClick = { onShowButtonClicked(photo) })
                    if (delete) {
                        DeletePhotoButton(onClick = { onSaveOrDeleteButtonClicked(photo)})
                    } else {
                        SavePhotoButton(onClick = { onSaveOrDeleteButtonClicked(photo)})
                    }

                }
            }
        }
    }
}

@Composable
fun ItemCard(
    item: Photo,
    delete: Boolean,
    onSaveOrDeleteButtonClicked: (Photo) -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(item.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = item.title,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    //ShowPhotoButton(onClick = { onShowButtonClicked(item) })
                    if (delete) {
                        DeletePhotoButton(onClick = { onSaveOrDeleteButtonClicked(item)})
                    } else {
                        SavePhotoButton(onClick = { onSaveOrDeleteButtonClicked(item)})
                    }
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
        PhotosGridScreen(mockData, delete = true, onShowButtonClicked = {}, onSaveOrDeleteButtonClicked = {})
        PhotosGridScreen(mockData, delete = false, onShowButtonClicked = {}, onSaveOrDeleteButtonClicked = {})
    }
}

