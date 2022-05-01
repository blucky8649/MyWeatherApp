package com.example.myweatherapp.data.source.local

import com.example.myweatherapp.model.WeatherItem

class WeatherLocalDataSource(
    private val db: WeatherDatabase
) {
    fun getWeather(): List<WeatherItem> {
        return db.getWeatherDao().getAll()
    }

    suspend fun insert(weatherItems: List<WeatherItem>) {
        db.getWeatherDao().insertAll(weatherItems)
    }

    suspend fun clear() {
        db.getWeatherDao().clear()
    }
}