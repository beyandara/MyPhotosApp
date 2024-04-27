package com.example.albumapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.albumapp.model.Photo
import com.example.albumapp.ui.theme.AlbumAppTheme

@Composable
fun SelectedPhotoScreen(
    photo: Photo,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (!isLandscape) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 90.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectedPhotoCard(photo = photo)
            SelectedPhotoDetails(photo = photo)
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 90.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Box(modifier = modifier.weight(1f)) {
                SelectedPhotoCard(photo = photo)
            }
            Box(modifier = modifier.weight(1f)) {
                SelectedPhotoDetails(photo = photo)
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
fun SelectedPhotoDetails(photo: Photo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(modifier = modifier.padding(10.dp)) {

            Text(text = stringResource(R.string.photo_details),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.id, photo.id))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = stringResource(R.string.title, photo.title))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = stringResource(R.string.album_id, photo.albumId))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = stringResource(R.string.Album_title))

        }
    }
}
@Preview(showBackground = true)
@Composable
fun SelectedPhotoScreenPreview() {
    AlbumAppTheme {
        val mockData =  Photo(1, 1,"title_test","url", "imgSrc")
        SelectedPhotoScreen(mockData)
    }
}
@Preview(showBackground = true, widthDp = 640, heightDp = 400)
@Composable
fun SelectedPhotoScreenLandscapePreview() {
    AlbumAppTheme {
        val mockData =  Photo(1, 1,"title_test","url", "imgSrc")
        SelectedPhotoScreen(mockData)
    }
}
