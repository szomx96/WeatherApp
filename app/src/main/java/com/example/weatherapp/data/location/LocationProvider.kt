package com.example.weatherapp.data.location

import com.example.weatherapp.data.db.entity.Location


interface LocationProvider {

    suspend fun hasLocationChanged(lastLocation : Location) : Boolean
    suspend fun getPreferredLocationString() : com.example.weatherapp.internal.DeviceLocation

}