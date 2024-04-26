package com.example.albumapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Entity data class represents a single row in the database.
 */

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val imgSrc: String,
    val thumbnailUrl: String,
    val albumId: Int
)
