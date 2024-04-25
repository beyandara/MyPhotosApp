package com.example.albumapp.data



import com.example.albumapp.model.Photo
import com.example.albumapp.network.AlbumApiService

/**
 * Repository retrieves photo data from underlying data source.
 */
interface AlbumRepository {
    /** Retrieves list of album from underlying data source */
    suspend fun getAlbum(): List<Photo>
}

/**
 * Network Implementation of repository that retrieves photo data from underlying data source.
 */
class DefaultAlbumRepository(
    private val albumApiService: AlbumApiService
) : AlbumRepository {
    /** Retrieves list of album from underlying data source */
    override suspend fun getAlbum(): List<Photo> = albumApiService.getAlbum()
}
