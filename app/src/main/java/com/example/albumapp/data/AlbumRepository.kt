package com.example.albumapp.data



import com.example.albumapp.model.Photo
import com.example.albumapp.network.AlbumApiService
import kotlinx.coroutines.flow.Flow


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

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface ItemsRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Item?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Item)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(item: Item)

}




















