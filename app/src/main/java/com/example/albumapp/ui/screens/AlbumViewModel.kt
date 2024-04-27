package com.example.albumapp.ui.screens


//import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
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
import com.example.albumapp.data.Item
import com.example.albumapp.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

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

    // Mutable state for the list of saved items
    private val _savedItems = MutableStateFlow<List<Photo>>(emptyList())
    val savedItems: StateFlow<List<Photo>> get() = _savedItems

//    var uiState: UiState by mutableStateOf(uiState)
    /**
     * Call getAlbum on init so we can display status immediately.
     */
    init {
        getAlbum()
    }

    private var _selectedPhoto: Photo? = null

    val selectedPhoto: Photo
        get() = _selectedPhoto!!

        fun setSelectedPhoto(photo : Photo) {
            _selectedPhoto = photo
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

    // Function to update the list of saved items
    fun updateSavedItems(items: List<Photo>) {
        _savedItems.value = items
    }


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
