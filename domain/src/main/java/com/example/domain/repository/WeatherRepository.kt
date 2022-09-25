package com.example.domain.repository

import com.example.domain.Resource
import com.example.domain.entity.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun observeWeatherListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Weather>>>
}