package com.example.albumapp.ui.screens


import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    onDeleteButtonClicked: (Photo) -> Unit,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel : AlbumViewModel = viewModel(factory = AlbumViewModel.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    when (albumUiState) {
        is AlbumUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AlbumUiState.Success -> AlbumScreenLayout(
            albumUiState.photos,
            //savedPhotosList = viewModel.homeUiState.collectAsState().value.savedPhotoList,
            savedPhotosList = homeUiState.savedPhotoList,
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
    savedPhotosList: List<Photo>,
    onShowButtonClicked: (Photo) -> Unit,
    onSaveButtonClicked: (Photo) -> Unit,
    onDeleteButtonClicked: (Photo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (!isLandscape) {
        Column() {
            Box(modifier = modifier.weight(1f)) {
                EvaluateSavedPhotosList(
                    savedPhotosList = savedPhotosList,
                    onShowButtonClicked = onShowButtonClicked,
                    onDeleteButtonClicked = onDeleteButtonClicked,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            }
            Divider(color = Color.Black, thickness = 3.dp)
            Box(modifier = modifier.weight(1f)) {
                PhotosGridScreen(
                    photos = photos,
                    delete = false,
                    onShowButtonClicked = onShowButtonClicked,
                    onSaveOrDeleteButtonClicked = onSaveButtonClicked,
                    contentPadding = contentPadding,
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }
    } else {
        Row {
            Box(modifier = modifier.weight(1f)) {
                EvaluateSavedPhotosList(
                    savedPhotosList = savedPhotosList,
                    onShowButtonClicked = onShowButtonClicked,
                    onDeleteButtonClicked = onDeleteButtonClicked,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
                Divider(color = Color.Black, thickness = 3.dp)

            }
            Box(modifier = modifier.weight(1f)) {
                PhotosGridScreen(
                    photos = photos,
                    delete = false,
                    onShowButtonClicked = onShowButtonClicked,
                    onSaveOrDeleteButtonClicked = onSaveButtonClicked,
                    contentPadding = contentPadding,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}

//@Composable
//fun AlbumScreenContent(
//    photos: List<Photo>,
//    savedPhotosList: List<Photo>,
//    onShowButtonClicked: (Photo) -> Unit,
//    onSaveButtonClicked: (Photo) -> Unit,
//    onDeleteButtonClicked: (Photo) -> Unit,
//    contentPadding: PaddingValues,
//    modifier: Modifier
//) {
//    Box(modifier = modifier.weight(1f)) {
//        EvaluateSavedPhotosList(
//            savedPhotosList = savedPhotosList,
//            onShowButtonClicked = onShowButtonClicked,
//            onDeleteButtonClicked = onDeleteButtonClicked,
//            contentPadding = contentPadding,
//            modifier = modifier
//        )
//    }
//    Divider(color = Color.Black, thickness = 3.dp)
//    Box(modifier = modifier.weight(1f)) {
//        PhotosGridScreen(
//            photos = photos,
//            delete = false,
//            onShowButtonClicked = onShowButtonClicked,
//            onSaveOrDeleteButtonClicked = onSaveButtonClicked,
//            contentPadding = contentPadding,
//            modifier = modifier.fillMaxWidth()
//        )
//    }
//}

@Composable
fun EvaluateSavedPhotosList(
    savedPhotosList : List<Photo>,
    onShowButtonClicked: (Photo) -> Unit,
    onDeleteButtonClicked: (Photo) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier) {
    if (savedPhotosList.isEmpty()) {
        Text(
            text = stringResource(R.string.no_saved_photos),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(contentPadding)
        )
    } else {
        PhotosGridScreen(
            photos = savedPhotosList,
            delete = true,
            onShowButtonClicked = onShowButtonClicked,
            onSaveOrDeleteButtonClicked = onDeleteButtonClicked,
            contentPadding = contentPadding,
            modifier = modifier
                .fillMaxWidth()
        )
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
//    LazyColumn(
//        modifier = modifier.padding(horizontal = 4.dp),
//        contentPadding = contentPadding,
//    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(1), // Angi antall kolonner her
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    )
    {
        items(
            items = photos,
            key = { photo ->
                photo.id
            }
        ) { photo ->
            PhotoCard(
                savedPhotosList = photos,
                photo = photo,
                delete = delete,
                onShowButtonClicked = onShowButtonClicked,
                onSaveOrDeleteButtonClicked = onSaveOrDeleteButtonClicked,
                modifier = modifier
                    .padding(2.dp)
                    .fillMaxWidth()
//                    .aspectRatio(1.5f)
            )
        }
    }
}

@Composable
fun PhotoCard(
    savedPhotosList: List<Photo>,
    photo: Photo,
    delete: Boolean,
    onShowButtonClicked: (Photo) -> Unit,
    onSaveOrDeleteButtonClicked: (Photo) -> Unit,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.heightIn(max = 60.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            border = BorderStroke(1.dp, Color.Black),
            modifier = modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

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
                Column {
                    Row(modifier = modifier.padding(top = 2.dp)) {
                        Text(
                            text = stringResource(R.string.title),
                            style = MaterialTheme.typography.labelLarge
                        )
                        val title = photo.title.take(15)
                        Text(
                            text = if (photo.title.length > 15) "$title..." else photo.title,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 3.dp, end = 2.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { onShowButtonClicked(photo) }) {
                            Text(
                                text = stringResource(R.string.show),
                                style = MaterialTheme.typography.labelSmall) }
                        if (delete) {
                            Button(onClick = { onSaveOrDeleteButtonClicked(photo) }
                            ) {
                                Text(
                                    text = stringResource(R.string.delete),
                                    style = MaterialTheme.typography.labelSmall) }
                        } else { Button(onClick = { onSaveOrDeleteButtonClicked(photo) }
                            ) {
                                Text(
                                    text = stringResource(R.string.save),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}

//
//@Composable
//fun ShowPhotoButton(
//    photo: Photo,
//
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Button(
//        onClick = {onClick()
//            println("Saved photo id: ${photo.id}")},
//
//        modifier = modifier
//    ) {
//        Text(stringResource(R.string.show))
//    }
//}

//@Composable
//fun DeletePhotoButton(
//    photo: Photo,
//
//    savedPhotosList: List<Photo>,
//
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Button(
//        onClick = {onClick()
//        println("Saved Photos List size: ${savedPhotosList.size}")
//        println("deleted photo id: ${photo.id}")},
//        modifier = modifier
//    ) {
//        Text(stringResource(R.string.delete))
//    }
//}
//
//@Composable
//fun SavePhotoButton(
//    photo: Photo,
//    savedPhotosList: List<Photo>,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Button(
//        onClick = {
//            onClick()
//            println("Saved Photos  List size: ${savedPhotosList.size}")
//            println("Saved photo id: ${photo.id}")},
//        modifier = modifier
//    ) {
//        Text(stringResource(R.string.save))
//    }
//}
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


@Preview(widthDp = 640, heightDp = 360)
@Composable
fun AlbumScreenLayoutLandscapePreview() {
    val photosMockData = List(15) { Photo(it, it,"title_test","url", "imgSrc") }
    val itemsMockData = List(5) { Photo(it, it,"title_test1111","u1111rl", "i1111mgSrc") }
    val modifier: Modifier = Modifier
    val contentPadding: PaddingValues = PaddingValues(0.dp)


    AlbumScreenLayout(
        photos = photosMockData,
        savedPhotosList = itemsMockData,
        onShowButtonClicked = {},
        onSaveButtonClicked = {},
        onDeleteButtonClicked = {},
        contentPadding = contentPadding,
        modifier = modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun AlbumScreenLayoutPortraitPreview() {
    val photosMockData = List(15) { Photo(it, it,"title_test","url", "imgSrc") }
    val itemsMockData = List(5) { Photo(it, it,"title_test","url", "mgSrc") }
    val modifier: Modifier = Modifier
    val contentPadding: PaddingValues = PaddingValues(0.dp)

    AlbumScreenLayout(
        photos = photosMockData,
        savedPhotosList = itemsMockData,
        onShowButtonClicked = {},
        onSaveButtonClicked = {},
        onDeleteButtonClicked = {},
        contentPadding = contentPadding,
        modifier = modifier.fillMaxWidth()
    )
}
























//
//
//@Composable
//fun AlbumScreenLayout(
//    photos: List<Photo>,
//    savedItems: List<Item>,
//    onShowButtonClicked: (Photo) -> Unit,
//    onSaveButtonClicked: (Photo) -> Unit,
//    onDeleteButtonClicked: (Item) -> Unit,
//    contentPadding: PaddingValues,
//    modifier: Modifier = Modifier
//) {
//    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
//
//    if (!isLandscape) {
//        Column() {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(top = 16.dp)
//            ) {
//                if (savedItems.isEmpty()) {
//                    Text(
//                        text = stringResource(R.string.no_saved_photos),
//                        textAlign = TextAlign.Center,
//                        style = MaterialTheme.typography.titleLarge,
//                        modifier = Modifier
//                            .padding(contentPadding)
//                    )
//                } else {
//                    SavedPhotosGridScreen(
//                        itemList = savedItems,
//                        delete = true,
//                        onSaveOrDeleteButtonClicked = onDeleteButtonClicked,
//                        contentPadding = contentPadding,
//                        modifier = Modifier.padding(horizontal = 4.dp)
//                    )
//                }
//            }
//            Divider(color = Color.Black, thickness = 3.dp)
//            Column(
//                modifier = Modifier.weight(1.5f)
//            ) {
//                PhotosGridScreen(
//                    photos,
//                    delete = false,
//                    onShowButtonClicked = onShowButtonClicked,
//                    onSaveOrDeleteButtonClicked = onSaveButtonClicked,
//                    contentPadding = contentPadding,
//                    modifier = modifier
//                        .fillMaxWidth()
//                )
//            }
//        }
//    } else {
//        Row {
//            Box(modifier = modifier.weight(1f)) {
//                SavedPhotosGridScreen(
//                    itemList = savedItems,
//                    delete = true,
//                    onSaveOrDeleteButtonClicked = onDeleteButtonClicked,
//                    contentPadding = contentPadding,
//                    modifier = Modifier.padding(horizontal = 4.dp)
//                )
//                Divider(color = Color.Black, thickness = 3.dp)
//            }
//            Box(modifier = modifier.weight(1f)) {
//                PhotosGridScreen(
//                    photos = photos,
//                    delete = false,
//                    onShowButtonClicked = onShowButtonClicked,
//                    onSaveOrDeleteButtonClicked = onSaveButtonClicked,
//                    contentPadding = contentPadding,
//                    modifier = modifier.fillMaxWidth()
//                )
//            }
//        }
//    }
//}
