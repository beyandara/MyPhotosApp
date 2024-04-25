package com.example.albumapp.data



import com.example.albumapp.model.Photo
import com.example.albumapp.network.AlbumApiService


/**
 * Repository retrieves photo data from underlying data source.
 */
interface AlbumRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun getAlbum(): List<Photo>
}

/**
 * Network Implementation of Repository that fetch mars photos list from marsApi.
 */
class NetworkAlbumRepository(
    private val albumApiService: AlbumApiService
) : AlbumRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getAlbum(): List<Photo> = albumApiService.getPhotos()
}






















