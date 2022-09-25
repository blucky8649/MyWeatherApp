package com.example.data.data.source.remote

import com.example.data.data.source.remote.dto.WeatherDetailDto
import com.example.data.data.source.remote.dto.WeatherDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("api/location/search")
    suspend fun getWeatherResponse(
        @Query("query") query: String
    ) : Response<List<WeatherDto>>

    @GET("api/location/{woeid}")
    suspend fun getWeatherDetail(
        @Path("woeid") woeid: Int
    ) : WeatherDetailDto
}