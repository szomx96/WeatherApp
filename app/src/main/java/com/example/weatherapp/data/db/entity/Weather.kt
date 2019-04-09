package com.example.weatherapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class Weather(
    val cityName: String,
    val weatherDesc: String,
    val temp: Int,
    val tempMin: Int,
    val tempMax: Int,
    val pressure: Int,
    val humidity: Int,
    val lon : Double,
    val lat : Double,
    val code : Int
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}