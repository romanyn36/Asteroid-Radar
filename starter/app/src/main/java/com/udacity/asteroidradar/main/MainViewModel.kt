package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.AsteroidFilter
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import timber.log.Timber


class MainViewModel(app: Application) : ViewModel() {
        private val _connection=MutableLiveData<Boolean>()
        val connection:LiveData<Boolean>
        get()=_connection
        val filter=MutableLiveData(AsteroidFilter.SHOW_SAVED)
        private val database=AsteroidDatabase.getDatabase(app)// create database
        private val repository=Repository(database)
        val image=repository.image
    fun isNetworkAvailable(context: Context): Boolean {// needs manifest permission
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
            .isConnected
    }


    init {
         Timber.plant(Timber.DebugTree())


        _connection.value=false
    viewModelScope.launch {
        if (isNetworkAvailable(app))  // check for connection status
        {
            repository.refresh()
            Timber.i("good connection")
        }

        else
        {
            _connection.value=true

            Timber.i("No enternet connection")
        }
    }


    }
    fun connecctivity()
    {
        _connection.value=false
    }
/////////////// filter

    val aasteroid = Transformations.switchMap(filter){
        when (it) {
            AsteroidFilter.SHOW_TODAY -> repository.asteroidToday
            AsteroidFilter.SHOW_WEEK -> repository.asteroidWeek
            else -> repository.asteroidAll
        }
    }
fun applyFilter(selectedDateItem: AsteroidFilter)
{
    filter.value=selectedDateItem
}

   }
class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}