package com.example.data.data.source.local

import com.example.data.data.source.mapper.toWeather
import com.example.domain.entity.Weather
import com.example.myweatherapp.model.entity.WeatherEntity
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(
    private val db: WeatherDatabase
): LocalDataSource {
    override fun getWeather(): List<Weather> {
        return db.getWeatherDao().getAll().map { it.toWeather() }
    }

    override suspend fun insert(weatherLocals: List<WeatherEntity>) {
        db.getWeatherDao().insertAll(weatherLocals)
    }

    override suspend fun clear() {
        db.getWeatherDao().clear()
    }
}