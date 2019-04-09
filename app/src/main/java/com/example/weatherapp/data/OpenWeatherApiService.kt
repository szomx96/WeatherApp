package com.example.weatherapp.data

import com.example.weatherapp.data.db.entity.Weather
import com.example.weatherapp.data.db.response.CurrentWeatherResponse
import com.example.weatherapp.network.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.serjltt.moshi.adapters.FirstElement
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "53c2e51466a4c7121b7aa9ae9fe6e3ab"

//api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=53c2e51466a4c7121b7aa9ae9fe6e3ab

interface OpenWeatherApiService {

    @GET("weather")
    fun getCurrentWeather(
        @Query("q") location : String?,
        @Query("lat") latitude : Double?,
        @Query("lon") longitude : Double?,
        @Query("units") unit: String = "metric"
    ) : Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : OpenWeatherApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("APPID", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            val moshi = Moshi.Builder().add(Wrapped.ADAPTER_FACTORY).add(FirstElement.ADAPTER_FACTORY).build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(OpenWeatherApiService::class.java)
        }
    }
}