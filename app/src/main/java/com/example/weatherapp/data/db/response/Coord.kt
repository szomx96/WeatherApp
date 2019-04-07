package com.example.weatherapp.data.db.response

import com.squareup.moshi.Json

data class Coord(
    @field:Json(name = "lon")
    val coordLat: Double,
    @field:Json(name = "lat")
    val coordLon: Double
)