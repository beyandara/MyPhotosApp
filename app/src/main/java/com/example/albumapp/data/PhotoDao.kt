package com.example.albumapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.albumapp.model.Photo
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface PhotoDao {

    @Query("SELECT * from items ORDER BY id ASC")
    fun getAllItems(): Flow<List<Photo>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Photo>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: Photo)

    @Delete
    suspend fun delete(item: Photo)
}