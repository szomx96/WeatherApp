package com.example.weatherapp.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.weatherapp.data.db.entity.Location
import com.example.weatherapp.internal.DeviceLocation
import com.example.weatherapp.internal.LocationPermissionNotGrantedException
import com.example.weatherapp.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastLocation: Location): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastLocation)
        }catch (e: LocationPermissionNotGrantedException){
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastLocation)
    }

    override suspend fun getPreferredLocationString(): com.example.weatherapp.internal.DeviceLocation {
        if(isUsingDeviceLocation()){
            try{
                val deviceLocation = getLastDeviceLocation().await()
                ?: return DeviceLocation.Custom(getCustomLocationName()!!)
                return DeviceLocation.Device(deviceLocation.latitude,deviceLocation.longitude)
            }catch (e: LocationPermissionNotGrantedException){
                return DeviceLocation.Custom(getCustomLocationName()!!)
            }
        }else
            return DeviceLocation.Custom(getCustomLocationName()!!)
    }

    private suspend fun hasDeviceLocationChanged(lastLocation: Location) : Boolean{

        if(!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await()
            ?: return false

        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastLocation.coordLat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastLocation.coordLon) > comparisonThreshold
    }

    private fun hasCustomLocationChanged(lastLocation: Location) : Boolean{
        val customLocationName = getCustomLocationName()
        return customLocationName != lastLocation.cityName
    }

    private fun getCustomLocationName() : String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    private fun isUsingDeviceLocation() : Boolean{
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation() : Deferred<android.location.Location?>{

        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()

    }

    private fun hasLocationPermission(): Boolean{
        return ContextCompat.checkSelfPermission(appContext,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED    }
}