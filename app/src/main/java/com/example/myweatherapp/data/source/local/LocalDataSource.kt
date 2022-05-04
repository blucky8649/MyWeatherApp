package com.example.myweatherapp.data.source.local

import com.example.myweatherapp.model.entity.WeatherEntity

interface LocalDataSource {
    fun getWeather(): List<WeatherEntity>
    suspend fun insert(weatherLocals: List<WeatherEntity>)
    suspend fun clear()
}