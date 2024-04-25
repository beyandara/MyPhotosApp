package com.example.albumapp.ui.screens


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.albumapp.AlbumApplication
import com.example.albumapp.data.AlbumRepository
import com.example.albumapp.model.Photo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
/**
 * UI state for the Home screen
 */
sealed interface AlbumUiState {
    data class Success(val photos: List<Photo>) : AlbumUiState
    object Error : AlbumUiState
    object Loading : AlbumUiState
}

class AlbumViewModel(private val albumRepository: AlbumRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var albumUiState: AlbumUiState by mutableStateOf(AlbumUiState.Loading)
        private set

    /**
     * Call getAlbum on init so we can display status immediately.
     */
    init {
        getAlbum()
    }


    fun getAlbum() {
        viewModelScope.launch {
            albumUiState = AlbumUiState.Loading
            albumUiState = try {
                AlbumUiState.Success(AlbumRepository.)
            } catch (e: IOException) {
                AlbumUiState.Error
            } catch (e: HttpException) {
                AlbumUiState.Error
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AlbumApplication)
                val albumRepository = application.container.albumRepository
                AlbumViewModel(albumRepository = albumRepository)
            }
        }
    }
}
