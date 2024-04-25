package com.example.albumapp.network

import com.example.albumapp.model.Photo
import retrofit2.http.GET

interface AlbumApiService {
    @GET("photo")
    suspend fun getAlbum(): List<Photo>
}