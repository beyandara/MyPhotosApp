package com.example.albumapp.network

import com.example.albumapp.model.Album
import com.example.albumapp.model.Photo
import retrofit2.http.GET



/**
 * A public interface that exposes the [getPhotos] method
 */
interface AlbumApiService {

    @GET("photos")
    suspend fun getPhotos(): List<Photo>

    @GET("albums")
    suspend fun getAlbums(): List<Album>
}
