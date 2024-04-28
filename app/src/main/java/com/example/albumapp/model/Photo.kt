

package com.example.albumapp.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "items")
@Serializable
data class Photo(
    val albumId: Int,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    @SerialName(value = "url")
    val imgSrc: String,
    val thumbnailUrl: String
)

@Serializable
data class Album(
    val userId: Int,
    val id: Int,
    val title: String
)

