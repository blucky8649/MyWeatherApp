package com.example.myweatherapp.data.source.remote

import com.example.myweatherapp.model.WeatherItem
import retrofit2.Response

class WeatherRemoteDataSource(
    private val api: WeatherApi
) {
    suspend fun getWeather(query: String): Response<List<WeatherItem>> {
        return api.getWeatherResponse(query)
    }
}