package com.example.weatherapp.data.db.response

import com.squareup.moshi.Json

data class Weather(
    @field:Json(name = "description")
    val weatherDesc: String,
    @field:Json(name = "icon")
    val weatherIcon: String,
    @field:Json(name = "id")
    val weatherId: Int,
    @field:Json(name = "main")
    val weatherMain: String
)