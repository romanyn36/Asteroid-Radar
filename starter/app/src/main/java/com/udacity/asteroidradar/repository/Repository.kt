package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Dates
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainObj
import com.udacity.asteroidradar.database.asImageDomain
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.Image
import com.udacity.asteroidradar.network.asDatabaseObj
import com.udacity.asteroidradar.network.asImageDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber
import java.util.*


class Repository(val database: AsteroidDatabase) {
    val date=Calendar.getInstance().time// your machine time
    val startDate=Dates.convertDateStringToFormattedString(date,Constants.API_QUERY_DATE_FORMAT)
    val addedDays=Dates.addDaysToDate(date,7)
    val endDate=Dates.convertDateStringToFormattedString(addedDays,Constants.API_QUERY_DATE_FORMAT)

    val asteroidAll: LiveData<List<Asteroid>> =Transformations.map(database.asteroidDAO.getAsteroid()){
        it.asDomainObj() // return Asteroid domain obj

    }
    val asteroidToday:LiveData<List<Asteroid>> =Transformations.map(database.asteroidDAO.getTodayAsteroid(startDate)){
        it.asDomainObj()
    }
    val asteroidWeek:LiveData<List<Asteroid>> =Transformations.map(database.asteroidDAO.getWeekAsteroid(startDate,endDate)){
        it.asDomainObj()
    }
///////////////
    val image:LiveData<Image> =Transformations.map(database.imageDao.getImageFromDb()){
        it?.asImageDomain()
    }


    suspend fun refresh()
    {

        withContext(Dispatchers.IO) {
            try {


                val asteroidJson = AsteroidApi.asteroidJson.getAsteroid()
                val jsonObject = JSONObject(asteroidJson)
                val asteroidsArrayList = parseAsteroidsJsonResult(jsonObject)
                database.asteroidDAO.insertAll(*asteroidsArrayList.asDatabaseObj())
                ///////////image
                val imageResponse = AsteroidApi.imageResponse.getImage()
                database.imageDao.insertImage(imageResponse.asImageDatabase())

            }catch (e:Exception)
            { Timber.plant(Timber.DebugTree())

                Timber.i("$e")
            }


    }
    }
    suspend fun delete()
    {
        withContext(Dispatchers.IO){
        database.asteroidDAO.delete(startDate)
    }
    }
}