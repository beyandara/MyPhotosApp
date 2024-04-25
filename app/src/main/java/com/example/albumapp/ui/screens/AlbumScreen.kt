package com.example.albumapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.albumapp.ui.theme.MyPhotosAppTheme
import com.example.myphotosapp.R

@Composable
fun AlbumScreen(
    albumUiState: AlbumUiState,
    onShowButtonClicked: () -> Unit,
    onSaveButtonClicked: () -> Unit,
    onDeleteButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .padding(4.dp)
                .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
        ) {
            Column {
                Text(
                    text = stringResource(R.string.saved_photos)
                )
                SavedPhotoCard()
            }
        }
        Box(
            modifier = modifier
                .padding(4.dp)
                .border(BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp)),
        ) {
            Column {
                Text(
                    text = "jsonplaceholder"
                )
                AvailablePhotoCard()
            }
        }
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
                painter = painterResource(androidx.core.R.drawable.ic_call_answer),
                contentDescription = null
            )
            ShowPhotoButton(onClick = { /*TODO*/ })
            DeletePhotoButton(onClick = { /*TODO*/ })
        }
    }
}

@Composable
fun AvailablePhotoCard(
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
                painter = painterResource(androidx.core.R.drawable.ic_call_answer),
                contentDescription = null
            )
            ShowPhotoButton(onClick = { /*TODO*/ })
            SavePhotoButton(onClick = { /*TODO*/ })
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

@Preview
@Composable
fun StartOrderPreview() {
    Surface(color = Color.White) {
        MyPhotosAppTheme {
//            AlbumScreen(
//                onShowButtonClicked = {},
//                onSaveButtonClicked = {},
//                onDeleteButtonClicked = {},
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(8.dp)
//            )
        }
    }
}