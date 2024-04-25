

package com.example.albumapp.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val albumId: Int,
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



/**  {
"albumId": 1,
"id": 1,
"title": "accusamus beatae ad facilis cum similique qui sunt",
"url": "https://via.placeholder.com/600/92c952",
"thumbnailUrl": "https://via.placeholder.com/150/92c952"
},

@Serializable
data class MarsPhoto(
val albumId: Int,
val id: Int,
val title: String,
@SerialName(value = "url")
val imgSrc: String,
val thumbnailUrl: String
)

 *****/