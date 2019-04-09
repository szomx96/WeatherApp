package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.CurrentWeatherDao
import com.example.weatherapp.data.db.LocationDao
import com.example.weatherapp.data.db.entity.Location
import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse
import com.example.weatherapp.data.location.LocationProvider
import com.example.weatherapp.internal.DeviceLocation
import com.example.weatherapp.internal.toLocation
import com.example.weatherapp.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val locationDao: LocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getLocation(): LiveData<Location> {
        return withContext(Dispatchers.IO) {
            return@withContext locationDao.getLocation()
        }
    }

    override suspend fun getCurrentWeather(): LiveData<out Weather> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: Weather) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather)
            locationDao.upsert(fetchedWeather.toLocation)
        }
    }

    private suspend fun initWeatherData() {

        val lastLocation = locationDao.getLocation().value

        if (lastLocation == null
            || locationProvider.hasLocationChanged(lastLocation)
        ) {
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        val deviceLocation = locationProvider.getPreferredLocationString()
        when (deviceLocation) {
            is DeviceLocation.Custom -> weatherNetworkDataSource.fetchCurrentWeather(
                deviceLocation.value, null, null
            )
            is DeviceLocation.Device -> weatherNetworkDataSource.fetchCurrentWeather(null,deviceLocation.latitude,deviceLocation.longitude)
        }

    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val halfHourAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(halfHourAgo)
    }

}
