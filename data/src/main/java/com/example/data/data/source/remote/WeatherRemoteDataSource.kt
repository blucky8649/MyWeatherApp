package com.example.data.data.source.remote

import com.example.data.data.source.remote.dto.WeatherDetailDto
import com.example.data.data.source.remote.dto.WeatherDto
import retrofit2.Response
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val api: WeatherApi
) : RemoteDataSource {
    override suspend fun getWeatherLocal(query: String): Response<List<WeatherDto>> {
        return api.getWeatherResponse(query)
    }
    override suspend fun getWeatherDetail(woeid: Int): WeatherDetailDto {
        return api.getWeatherDetail(woeid = woeid)
    }
}