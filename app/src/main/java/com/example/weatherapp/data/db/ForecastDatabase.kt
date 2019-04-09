package com.example.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.db.entity.Location
import com.example.weatherapp.data.db.entity.Weather


@Database(
    entities = [Weather::class, Location::class],
    version = 2
)
abstract class ForecastDatabase : RoomDatabase(){

    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun locationDao() : LocationDao

    companion object {

        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context)
                .also{ instance = it}
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                    ForecastDatabase::class.java,
                    "forecast.db")
                    .build()
    }
}
