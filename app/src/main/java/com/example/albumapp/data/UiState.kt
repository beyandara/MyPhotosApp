package com.example.albumapp.data

import com.example.albumapp.model.Photo

data class UiState(
    val mySavedPhotos: List<Photo> = mutableListOf()
)