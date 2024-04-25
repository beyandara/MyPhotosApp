package com.example.myphotosapp.ui

import androidx.lifecycle.ViewModel
import com.example.myphotosapp.data.AlbumUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlbumViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(AlbumUiState())
    val uiState: StateFlow<AlbumUiState> = _uiState.asStateFlow()
}