package com.example.weatherapp.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.OpenWeatherApiService
import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse
import com.example.weatherapp.internal.NoConnectivityException
import com.example.weatherapp.internal.toWeather

class WeatherNetworkDataSourceImpl(
    private val openWeatherApiService: OpenWeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<Weather>()

    override val downloadedCurrentWeather: LiveData<Weather>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
       try{
           val fetchedCurrentWeather = openWeatherApiService.getCurrentWeather(location)
               .await()
           _downloadedCurrentWeather.postValue(fetchedCurrentWeather.toWeather)
       }
       catch(e: NoConnectivityException){
           Log.e("Connectivity", "No internet connection", e)
       }
    }
}