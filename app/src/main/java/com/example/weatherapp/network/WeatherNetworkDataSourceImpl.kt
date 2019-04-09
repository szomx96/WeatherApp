package com.example.weatherapp.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.OpenWeatherApiService
import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse
import com.example.weatherapp.internal.NoConnectivityException
import com.example.weatherapp.internal.toWeather
import java.lang.Exception

class WeatherNetworkDataSourceImpl(
    private val openWeatherApiService: OpenWeatherApiService
) : WeatherNetworkDataSource {
    override suspend fun fetchCurrentWeather(location: String?, latitude: Double?, longitude: Double?) {
        try{
            val fetchedCurrentWeather = openWeatherApiService.getCurrentWeather(location,latitude,longitude)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather.toWeather)
        }
        catch(e: Exception){
            Log.e("DZIALA?", "NIE DZIAUA", e)
        }
    }

    private val _downloadedCurrentWeather = MutableLiveData<Weather>()

    override val downloadedCurrentWeather: LiveData<Weather>
        get() = _downloadedCurrentWeather


}