package com.example.weatherapp

import android.app.Application
import android.content.Context
import com.example.weatherapp.data.OpenWeatherApiService
import com.example.weatherapp.data.db.ForecastDatabase
import com.example.weatherapp.data.location.LocationProvider
import com.example.weatherapp.data.location.LocationProviderImpl
import com.example.weatherapp.data.repository.ForecastRepository
import com.example.weatherapp.data.repository.ForecastRepositoryImpl
import com.example.weatherapp.network.ConnectivityInterceptor
import com.example.weatherapp.network.ConnectivityInterceptorImpl
import com.example.weatherapp.network.WeatherNetworkDataSource
import com.example.weatherapp.network.WeatherNetworkDataSourceImpl
import com.example.weatherapp.ui.weather.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {

    override  val kodein = Kodein.lazy{
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance())}
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().locationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton{ LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance())}
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}