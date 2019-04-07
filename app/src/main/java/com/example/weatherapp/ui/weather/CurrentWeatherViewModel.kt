package com.example.weatherapp.ui.weather

import androidx.lifecycle.ViewModel;
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

}
