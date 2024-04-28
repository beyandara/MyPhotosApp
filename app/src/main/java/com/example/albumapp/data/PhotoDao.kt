package com.example.albumapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.albumapp.model.Photo
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the item database
 */
@Dao
interface PhotoDao {

    @Query("SELECT * from items ORDER BY id ASC")
    fun getAllItems(): Flow<List<Photo>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Photo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Photo)

    @Delete
    suspend fun delete(item: Photo)
}