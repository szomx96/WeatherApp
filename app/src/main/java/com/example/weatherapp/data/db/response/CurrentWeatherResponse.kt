package com.example.weatherapp.data.db.response

import com.serjltt.moshi.adapters.FirstElement
import com.squareup.moshi.Json

data class CurrentWeatherResponse(

    val clouds: Clouds,
    val coord: Coord,
    val main: Main,
    val sys: Sys,
    @FirstElement
    val weather: Weather,
    val wind: Wind,
    val visibility: Int,
    val base: String,
    val dt: Int,
    @field:Json(name = "id")
    val weatherId: Int,
    @field:Json(name = "name")
    val cityName: String,
    @field:Json(name = "cod")
    val weatherCod: Int
)