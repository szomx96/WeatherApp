package com.example.weatherapp.internal

sealed class DeviceLocation {
    data class Device(val latitude: Double, val longitude: Double) : DeviceLocation()
    data class Custom(val value: String) : DeviceLocation()
}