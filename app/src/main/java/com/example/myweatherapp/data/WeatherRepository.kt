package com.example.myweatherapp.data

import com.example.myweatherapp.model.WeatherItem
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun observeWeatherListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<WeatherItem>>>
}