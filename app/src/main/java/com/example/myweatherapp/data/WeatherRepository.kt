package com.example.myweatherapp.data

import com.example.myweatherapp.model.WeatherLocal
import com.example.myweatherapp.model.entity.WeatherEntity
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun observeWeatherListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<WeatherEntity>>>
}