package com.example.data.data.source.remote

import com.example.data.data.source.remote.dto.WeatherDetailDto
import com.example.data.data.source.remote.dto.WeatherDto
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getWeatherLocal(query: String): Response<List<WeatherDto>>
    suspend fun getWeatherDetail(woeid: Int): WeatherDetailDto
}