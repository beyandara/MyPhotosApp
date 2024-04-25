package com.example.albumapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.albumapp.ui.AlbumApp
import com.example.albumapp.ui.theme.AlbumAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AlbumAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    AlbumApp()
                }
            }
        }
    }
}
