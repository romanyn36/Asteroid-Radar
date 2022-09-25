package com.udacity.asteroidradar.api

import androidx.databinding.library.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.network.ImageNetwork
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
/////////////
private val moshi= Moshi.Builder().
        add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory()).build()


private val retrofit= Retrofit.Builder().
    addConverterFactory(ScalarsConverterFactory.create()).
    addConverterFactory(MoshiConverterFactory.create(moshi)).
    baseUrl(Constants.BASE_URL).build()
//private val nasaApiKey = BuildConfig.NASA_API_KEY



interface AsteroidJson
{// start_date=2015-09-07&end_date=2015-09-08&
@GET("neo/rest/v1/feed?api_key=EJS3dp5eH9qySXtohgs4ex5a4bFePstS4BtO3Vfi")
suspend fun getAsteroid():String

}

interface ImageOfTheDay
{// start_date=2015-09-07&end_date=2015-09-08&
    @GET("planetary/apod?api_key=EJS3dp5eH9qySXtohgs4ex5a4bFePstS4BtO3Vfi")
    suspend fun getImage():ImageNetwork

}
object AsteroidApi
{
    val asteroidJson:AsteroidJson by lazy{
        retrofit.create(AsteroidJson::class.java)
    }
    val imageResponse:ImageOfTheDay by lazy{
        retrofit.create(ImageOfTheDay::class.java)
    }
}

