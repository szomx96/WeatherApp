package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.entity.Location
import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse

interface ForecastRepository {

    suspend fun getCurrentWeather(): LiveData<out Weather>
    suspend fun getLocation() : LiveData<Location>
}