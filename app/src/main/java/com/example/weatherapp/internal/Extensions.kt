package com.example.weatherapp.internal

import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse
import kotlin.math.roundToInt


internal val CurrentWeatherResponse.toWeather : Weather
    get() = Weather(
        cityName,
        weather.weatherDesc,
        main.temp.roundToInt(),
        main.tempMin.roundToInt(),
        main.tempMax.roundToInt(),
        main.pressure,
        main.humidity
    )