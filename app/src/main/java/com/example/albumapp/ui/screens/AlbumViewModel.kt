package com.example.albumapp.ui.screens


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.albumapp.AlbumApplication
import com.example.albumapp.data.AlbumRepository
import com.example.albumapp.model.Photo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface AlbumUiState {
    data class Success(val album: List<Photo>) : AlbumUiState
    data object Error : AlbumUiState
    data object Loading : AlbumUiState
}

/**
 * ViewModel containing the app data and method to retrieve the data
 */
class AlbumViewModel(private val albumRepository: AlbumRepository) : ViewModel() {

    var albumUiState: AlbumUiState by mutableStateOf(AlbumUiState.Loading)
        private set

    init {
        getAlbum()
    }

    fun getAlbum() {
        viewModelScope.launch {
            albumUiState = AlbumUiState.Loading
            albumUiState = try {
                AlbumUiState.Success(albumRepository.getAlbum())
            } catch (e: IOException) {
                AlbumUiState.Error
            } catch (e: HttpException) {
                AlbumUiState.Error
            }
        }
    }

    /**
     * Factory for [AlbumViewModel] that takes [AlbumRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AlbumApplication)
                val albumRepository = application.container.albumRepository
                AlbumViewModel(albumRepository = albumRepository)
            }
        }
    }
}
