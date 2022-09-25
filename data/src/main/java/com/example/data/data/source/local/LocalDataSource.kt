package com.example.data.data.source.local

import com.example.domain.entity.Weather
import com.example.myweatherapp.model.entity.WeatherEntity

interface LocalDataSource {
    fun getWeather(): List<Weather>
    suspend fun insert(weatherLocals: List<WeatherEntity>)
    suspend fun clear()
}