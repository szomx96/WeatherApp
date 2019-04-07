package com.example.weatherapp.data.db.response

import com.squareup.moshi.Json

data class Wind(
    @field:Json(name = "deg")
    val windDegree: Double,
    @field:Json(name = "speed")
    val windSpeed: Double
)