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
import com.example.albumapp.data.ItemsRepository
import com.example.albumapp.model.Album
import com.example.albumapp.model.Photo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface AlbumUiState {
    data class Success(val photos: List<Photo>, val album: List<Album>) : AlbumUiState
    object Error : AlbumUiState
    object Loading : AlbumUiState
}

class AlbumViewModel( val albumRepository: AlbumRepository, private val itemsRepository: ItemsRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var albumUiState: AlbumUiState by mutableStateOf(AlbumUiState.Loading)
        private set

    /**
     * Holds home ui state. The list of photos are retrieved from [ItemsRepository] and mapped to
     * [HomeUiState]
     */
    val homeUiState: StateFlow<HomeUiState> =
        itemsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )


    /**
     * Call getAlbum on init so we can display status immediately.
     */
    init {
        getAlbum()
    }

    private var _selectedPhoto: Photo? = null

    private var _selectedAlbum: Album? = null

    val selectedPhoto: Photo
        get() = _selectedPhoto!!

        fun setSelectedPhoto(photo : Photo) { // TODO fiks navn til setSelectedPhotoAlbum
            _selectedPhoto = photo
        }

    val selectedAlbum: Album
        get() = _selectedAlbum!!

        suspend fun setSelectedAlbum(photo : Photo) {
            val albumList = albumRepository.getAlbums()
            _selectedAlbum = findAlbumForPhoto(_selectedPhoto!!, albumList)
        }

    private suspend fun findAlbumForPhoto(photo: Photo, albums: List<Album>): Album? {
        return albums.find { it.id == photo.id }
    }
    fun getAlbum() {
        viewModelScope.launch {
            albumUiState = AlbumUiState.Loading
            albumUiState = try {
                AlbumUiState.Success(albumRepository.getAlbum(), albumRepository.getAlbums())
            } catch (e: IOException) {
                AlbumUiState.Error
            } catch (e: HttpException) {
                AlbumUiState.Error
            }
        }
    }
    /**
     * Insert/Delete a [Photo] in the Room database
     */
    suspend fun saveItem(selectedPhoto : Photo) {
        itemsRepository.insertItem(selectedPhoto)
    }

    suspend fun deleteItem(selectedPhoto : Photo) {
        itemsRepository.deleteItem(selectedPhoto)
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AlbumApplication)
                val albumRepository = application.container.albumRepository
                val itemsRepository = application.container.itemsRepository
                AlbumViewModel(albumRepository = albumRepository, itemsRepository = itemsRepository)
            }
        }
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val savedPhotoList: List<Photo> = listOf())
