package com.example.weatherapp.data.db.response

import com.squareup.moshi.Json

data class Clouds(
    @field:Json(name = "all")
    val cloudsAll: Int
)