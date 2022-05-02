package com.example.myweatherapp.data.source.local

import com.example.myweatherapp.model.WeatherLocal
import com.example.myweatherapp.model.entity.WeatherEntity

class WeatherLocalDataSource(
    private val db: WeatherDatabase
) {
    fun getWeather(): List<WeatherEntity> {
        return db.getWeatherDao().getAll()
    }

    suspend fun insert(weatherLocals: List<WeatherEntity>) {
        db.getWeatherDao().insertAll(weatherLocals)
    }

    suspend fun clear() {
        db.getWeatherDao().clear()
    }
}