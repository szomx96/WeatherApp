package com.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weather: Weather)

    @Query("SELECT * from weather_table")
    fun getWeather(): LiveData<Weather>
}