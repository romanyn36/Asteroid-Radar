package com.udacity.asteroidradar.network
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.database.AsteroidDatabaseObj
import com.udacity.asteroidradar.domain.Asteroid

data class AsteroidNetwork(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean)
fun ArrayList<Asteroid>.asDatabaseObj():Array<AsteroidDatabaseObj>
{
    return map {
        AsteroidDatabaseObj(
            id=it.id,
            codename =it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude =it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

