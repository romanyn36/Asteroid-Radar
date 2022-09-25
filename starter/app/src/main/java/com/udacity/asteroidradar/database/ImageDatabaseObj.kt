package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Image

@Entity
data class ImageDatabase(
    @PrimaryKey
    val url:String,
    val media_type:String,
    val title:String
)
fun ImageDatabase.asImageDomain():Image{
    return Image(
            url,
            media_type,
            title
    )
}