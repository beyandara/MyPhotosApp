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
    data class Success(val amphibians: List<Photo>) : AlbumUiState
    object Error : AlbumUiState
    object Loading : AlbumUiState
}

/**
 * ViewModel containing the app data and method to retrieve the data
 */
class AlbumViewModel(private val amphibiansRepository: AlbumRepository) : ViewModel() {

    var amphibiansUiState: AlbumUiState by mutableStateOf(AlbumUiState.Loading)
        private set

    init {
        getAlbum()
    }

    fun getAlbum() {
        viewModelScope.launch {
            amphibiansUiState = AlbumUiState.Loading
            amphibiansUiState = try {
                AlbumUiState.Success(amphibiansRepository.getAlbum())
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
                val amphibiansRepository = application.container.amphibiansRepository
                AlbumViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}
