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
import androidx.room.PrimaryKey
import com.example.albumapp.AlbumApplication
import com.example.albumapp.data.AlbumRepository
import com.example.albumapp.data.Item
import com.example.albumapp.data.ItemsRepository
import com.example.albumapp.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import retrofit2.HttpException
import java.io.IOException


sealed interface AlbumUiState {
    data class Success(val photos: List<Photo>) : AlbumUiState
    object Error : AlbumUiState
    object Loading : AlbumUiState
}

class AlbumViewModel(private val albumRepository: AlbumRepository, private val itemsRepository: ItemsRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var albumUiState: AlbumUiState by mutableStateOf(AlbumUiState.Loading)
        private set

    /**
     * Holds current item ui state
     */
    var photoUiState by mutableStateOf(PhotoUiState())
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
    /**
     * Inserts a [Photo] in the Room database
     */
    suspend fun saveItem() {
        itemsRepository.insertItem(photoUiState.photoDetails.toPhoto())
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

data class PhotoUiState(
    val photoDetails: PhotoDetails = PhotoDetails()
)
data class HomeUiState(val savedPhotoList: List<Photo> = listOf())

data class PhotoDetails(
    val albumId: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val imgSrc: String = "",
    val thumbnailUrl: String = ""
)

/**
 * Extension function to convert [PhotoUiState] to [Photo].
 */
fun PhotoDetails.toPhoto(): Photo = Photo(
    albumId = albumId,
    id = id,
    title = title,
    imgSrc = imgSrc,
    thumbnailUrl = thumbnailUrl
)

/**
 * Extension function to convert [Photo] to [PhotoUiState]
 */
fun Photo.toPhotoUiState(): PhotoUiState = PhotoUiState(
    photoDetails = this.toPhotoDetails()
)

/**
 * Extension function to convert [Photo] to [PhotoDetails]
 */
fun Photo.toPhotoDetails(): PhotoDetails = PhotoDetails(
    albumId = albumId,
    id = id,
    title = title,
    imgSrc = imgSrc,
    thumbnailUrl = thumbnailUrl
)