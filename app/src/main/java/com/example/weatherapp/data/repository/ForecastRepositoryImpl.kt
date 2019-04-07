package com.example.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.db.CurrentWeatherDao
import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse
import com.example.weatherapp.network.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init{
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<out Weather> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: Weather){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Los Angeles"
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime : ZonedDateTime) : Boolean{
       val halfHourAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(halfHourAgo)
    }

}
