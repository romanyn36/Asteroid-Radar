package com.udacity.asteroidradar.network
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.ImageDatabase


@JsonClass(generateAdapter = true)
data class ImageNetwork(
    val url:String,
    val media_type:String,
    val title:String
)

fun ImageNetwork.asImageDatabase():ImageDatabase
{
    return ImageDatabase(
        url,
        media_type,
        title
    )
}