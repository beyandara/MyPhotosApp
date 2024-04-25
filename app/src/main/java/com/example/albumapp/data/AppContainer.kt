package com.example.albumapp.data

import com.example.albumapp.network.AlbumApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val albumRepository: AlbumRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java)
    }

    /**
     * DI implementation for Album repository
     */
    override val albumRepository: AlbumRepository by lazy {
        DefaultAlbumRepository(retrofitService)
    }
}
