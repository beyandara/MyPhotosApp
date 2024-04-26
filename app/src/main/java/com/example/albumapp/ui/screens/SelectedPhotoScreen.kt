package com.example.albumapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
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
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        SelectedPhotoCard(photo = photo)
        SelectedPhotoDetails(photo = photo)
    }
}

@Composable
fun SelectedPhotoCard(photo: Photo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp),
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
            .padding(4.dp)
            .fillMaxSize(),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.photo_details),
                    fontWeight = FontWeight.Bold)
            }
            Text(text = stringResource(R.string.id, photo.id))
            Text(text = stringResource(R.string.title, photo.title))
            Text(text = stringResource(R.string.album_id, photo.albumId))
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