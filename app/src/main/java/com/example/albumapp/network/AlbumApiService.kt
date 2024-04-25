package com.example.albumapp.network

import com.example.albumapp.model.Album
import com.example.albumapp.model.Photo
import retrofit2.http.GET



/**
 * A public interface that exposes the [getPhotos] method
 */
interface AlbumApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("photos")
    suspend fun getPhotos(): List<Photo>

    @GET("albums")
    suspend fun getAlbums(): List<Album>
}
