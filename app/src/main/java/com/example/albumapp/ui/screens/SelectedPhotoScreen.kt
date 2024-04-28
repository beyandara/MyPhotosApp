package com.example.albumapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.albumapp.R
import com.example.albumapp.model.Album
import com.example.albumapp.model.Photo
import com.example.albumapp.ui.theme.AlbumAppTheme

@Composable
fun SelectedPhotoScreen(
    photo: Photo,
    album: Album,
    modifier: Modifier = Modifier,
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (!isLandscape) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectedPhotoCard(photo = photo)
            SelectedPhotoDetails(photo = photo, album=album)
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Box(modifier = modifier.weight(1f)) {
                SelectedPhotoCard(photo = photo)
            }
            Box(modifier = modifier.weight(1f)) {
                SelectedPhotoDetails(photo = photo, album=album)
            }
        }

    }
}

@Composable
fun SelectedPhotoCard(photo: Photo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(2.dp),
        border = BorderStroke(2.dp, Color.Black)
    ) {
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

        )
    }
}

@Composable
fun SelectedPhotoDetails(photo: Photo, album: Album, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .fillMaxSize(),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Row(modifier = modifier.padding(10.dp)) {
            Column {
                Text(
                    text = stringResource(R.string.photo_details),
                    style = MaterialTheme.typography.titleLarge
                )
                Row(modifier = modifier.padding(5.dp)) {
                    Text(
                        text = stringResource(R.string.id),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(100.dp)
                    )

                    Text(
                        text = photo.id.toString(),
                        modifier = Modifier.padding(start = 8.dp))
                }
                Row(modifier = modifier.padding(5.dp)) {
                    Text(
                        text = stringResource(R.string.title),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = photo.title,
                        modifier = Modifier.padding(start = 8.dp))
                }
                Row(modifier = modifier.padding(5.dp)) {
                    Text(
                        text = stringResource(R.string.album_id,),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(100.dp))
                    Text(
                        text = photo.albumId.toString(),
                        modifier = Modifier.padding(start = 8.dp))
                }
                Row(modifier = modifier.padding(5.dp)) {
                    Text(
                        text = stringResource(R.string.Album_title),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.width(100.dp)
                    )
                    Text(
                        text = album.title,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SelectedPhotoScreenPreview() {
    AlbumAppTheme {
        val mockData =  Photo(1, 1,"title_test","url", "imgSrc")
        val mockAlbum = Album(1,1, "title")
        SelectedPhotoScreen(mockData, mockAlbum)
    }
}
@Preview(showBackground = true, widthDp = 640, heightDp = 400)
@Composable
fun SelectedPhotoScreenLandscapePreview() {
    AlbumAppTheme {
        val mockData =  Photo(1, 1,"title_test","url", "imgSrc")
        val mockAlbum = Album(1,1, "title")
        SelectedPhotoScreen(mockData, mockAlbum)
    }
}
