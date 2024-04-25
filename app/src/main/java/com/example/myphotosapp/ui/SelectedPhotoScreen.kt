package com.example.myphotosapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myphotosapp.R
import com.example.myphotosapp.ui.theme.MyPhotosAppTheme

@Composable
fun SelectedPhotoScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = modifier
                .padding(4.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
        }
        Card(
            modifier = modifier
                .padding(4.dp),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text(
                text = stringResource(R.string.photo_details)
            )
        }
    }
}

@Preview
@Composable
fun SelectedPhotoPreview() {
    MyPhotosAppTheme {
        SelectedPhotoScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}