package com.example.albumapp.data

import com.example.albumapp.model.Photo
import kotlinx.coroutines.flow.Flow

class OfflinePhotosRepository(private val photoDao: PhotoDao) : ItemsRepository {
    override fun getAllItemsStream(): Flow<List<Photo>> = photoDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Photo?> = photoDao.getItem(id)

    override suspend fun insertItem(item: Photo) = photoDao.insert(item)

    override suspend fun deleteItem(item: Photo) = photoDao.delete(item)

}
