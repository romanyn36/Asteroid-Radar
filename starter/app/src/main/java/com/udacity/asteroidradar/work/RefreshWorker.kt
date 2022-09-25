package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.Repository

import retrofit2.HttpException

class RefreshWorker (appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {
        companion object {
            const val WORK_NAME = "RefreshDataWorker"
        }
        override suspend fun doWork(): Result { // check if failed fetch data

            val databaseVideo =AsteroidDatabase.getDatabase(applicationContext)
            val repository = Repository(databaseVideo)
            return try {

                repository.refresh()
                repository.delete()/// delete old
                Result.success()
            } catch (e: HttpException) {
                Result.retry()// retry
            }
        }

}