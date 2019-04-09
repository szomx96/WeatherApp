package com.example.weatherapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "location_table")
data class Location(
    val cityName: String,
    val coordLat: Double,
    val coordLon: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID
}