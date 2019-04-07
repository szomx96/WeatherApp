package com.example.weatherapp.ui.weather

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.weatherapp.R
import com.example.weatherapp.data.OpenWeatherApiService
import com.example.weatherapp.internal.glide.GlideApp
import com.example.weatherapp.network.ConnectivityInterceptor
import com.example.weatherapp.network.ConnectivityInterceptorImpl
import com.example.weatherapp.network.WeatherNetworkDataSourceImpl
import com.example.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware{

    override val kodein by closestKodein()
    private val viewModelFactory : CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch{
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Los Angeles")
            updateDateToToday()
            updateTemperatures(it.temp, it.tempMin, it.tempMax)
            updateWeatherDesc(it.weatherDesc)
            updatePressure(it.pressure)
            updateHumidity(it.humidity)

//            GlideApp.with(this@CurrentWeatherFragment)
//                .load("")
//                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location : String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature : Int,
                                   minTemp : Int,
                                   maxTemp : Int){
        textView_temperature.text = "$temperature°C"
        textView_min_temp.text = "$minTemp°C"
        textView_max_temp.text = "$maxTemp°C"
    }

    private fun updateWeatherDesc(weatherDesc : String){
        textView_weatherDesc.text = weatherDesc
    }

    private fun updatePressure(pressure : Int){
        textView_pressure.text = pressure.toString()
    }

    private fun updateHumidity(humidity : Int){
        textView_humidity.text = humidity.toString()
    }
}

//val cityName: String,
//val weatherDesc: String,
//val temp: Int,
//val tempMin: Int,
//val tempMax: Int,
//val pressure: Int,
//val humidity: Int
