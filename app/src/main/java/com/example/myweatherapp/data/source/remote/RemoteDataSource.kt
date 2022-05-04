package com.example.myweatherapp.data.source.remote

import com.example.myweatherapp.model.WeatherDetail
import com.example.myweatherapp.model.WeatherLocal
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getWeatherLocal(query: String): Response<List<WeatherLocal>>
    suspend fun getWeatherDetail(woeid: Int): WeatherDetail
}