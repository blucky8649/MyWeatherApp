package com.example.myweatherapp.data.source.remote

import com.example.myweatherapp.model.WeatherDetail
import com.example.myweatherapp.model.WeatherLocal
import retrofit2.Response

class WeatherRemoteDataSource(
    private val api: WeatherApi
) {
    suspend fun getWeatherLocal(query: String): Response<List<WeatherLocal>> {
        return api.getWeatherResponse(query)
    }
    suspend fun getWeatherDetail(woeid: Int): WeatherDetail {
        return api.getWeatherDetail(woeid = woeid)
    }
}