package com.example.weatherapp.network

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<Weather>

    suspend fun fetchCurrentWeather(
        location: String
    )
}