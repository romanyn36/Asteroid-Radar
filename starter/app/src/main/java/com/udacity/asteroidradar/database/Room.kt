package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDAO {
    @Query("select * from asteroiddatabaseobj where closeApproachDate between :startDate and :endDate order by closeApproachDate desc")
    fun getWeekAsteroid(startDate:String, endDate:String):LiveData<List<AsteroidDatabaseObj>>

    @Query("select * from asteroiddatabaseobj where closeApproachDate=:today order by closeApproachDate desc")
    fun getTodayAsteroid(today :String):LiveData<List<AsteroidDatabaseObj>>

    @Query("select * from asteroiddatabaseobj  order by closeApproachDate desc")
    fun getAsteroid():LiveData<List<AsteroidDatabaseObj>>

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertAll(vararg database: AsteroidDatabaseObj)
////////////////////////
    @Query("DELETE FROM asteroiddatabaseobj WHERE closeApproachDate<:today")
    fun delete(today:String)

}
@Dao
interface ImageDao{
    @Query("select * from imagedatabase")
    fun getImageFromDb():LiveData<ImageDatabase>

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertImage(imageDatabase: ImageDatabase)
}


@Database(entities = [AsteroidDatabaseObj::class,ImageDatabase::class], version = 1)
abstract class AsteroidDatabase :RoomDatabase()
{
    abstract val asteroidDAO:AsteroidDAO
    abstract val imageDao:ImageDao
    companion object{
            lateinit var INSTANCE:AsteroidDatabase
            fun getDatabase(context: Context):AsteroidDatabase
            {
                synchronized(AsteroidDatabase::class.java){
                    if (!::INSTANCE.isInitialized)
                    {
                        INSTANCE=Room.databaseBuilder(context.applicationContext,AsteroidDatabase::class.java,"asteroids").
                        build()
                    }

                }
            return INSTANCE
            }

            }
}
