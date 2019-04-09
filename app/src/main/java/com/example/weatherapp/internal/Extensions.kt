package com.example.weatherapp.internal

import com.example.weatherapp.R
import com.example.weatherapp.data.db.entity.Location
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
        main.humidity,
        coord.coordLon,
        coord.coordLat,
        icon[weather.weatherId]!!
    )

internal val Weather.toLocation : Location
    get() = Location(
        cityName,
        lon,
        lat
    )

val icon = hashMapOf(
    //sun
    800 to R.drawable.ic_sunny,
    //clouds
    801 to R.drawable.ic_cloudy,
    802 to R.drawable.ic_cloudy,
    803 to R.drawable.ic_cloudy,
    804 to R.drawable.ic_cloudy,
    //mist
    701 to R.drawable.ic_foggy,
    711 to R.drawable.ic_foggy,
    721 to R.drawable.ic_foggy,
    731 to R.drawable.ic_foggy,
    //snow
    600 to R.drawable.ic_snowy,
    601 to R.drawable.ic_snowy,
    602 to R.drawable.ic_snowy,
    611 to R.drawable.ic_snowy,
    612 to R.drawable.ic_snowy,
    613 to R.drawable.ic_snowy,
    615 to R.drawable.ic_snowy,
    616 to R.drawable.ic_snowy,
    620 to R.drawable.ic_snowy,
    621 to R.drawable.ic_snowy,
    622 to R.drawable.ic_snowy,
    //rain
    500 to R.drawable.ic_rainy,
    501 to R.drawable.ic_rainy,
    502 to R.drawable.ic_rainy,
    503 to R.drawable.ic_rainy,
    504 to R.drawable.ic_rainy,
    511 to R.drawable.ic_rainy,
    520 to R.drawable.ic_rainy,
    521 to R.drawable.ic_rainy,
    //Storm
    200 to R.drawable.ic_stormy,
    201 to R.drawable.ic_stormy,
    202 to R.drawable.ic_stormy,
    210 to R.drawable.ic_stormy,
    211 to R.drawable.ic_stormy,
    212 to R.drawable.ic_stormy,
    221 to R.drawable.ic_stormy,
    230 to R.drawable.ic_stormy,
    231 to R.drawable.ic_stormy,
    232 to R.drawable.ic_stormy

)