package com.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.db.entity.Location
import com.example.weatherapp.data.db.entity.WEATHER_LOCATION_ID

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(location: Location)

    @Query("select * from location_table where id = $WEATHER_LOCATION_ID")
    fun getLocation() : LiveData<Location>

}