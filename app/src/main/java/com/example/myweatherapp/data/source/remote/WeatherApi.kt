package com.example.myweatherapp.data.source.remote

import com.example.myweatherapp.model.ConsolidatedWeather
import com.example.myweatherapp.model.WeatherDetail
import com.example.myweatherapp.model.WeatherLocal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("api/location/search")
    suspend fun getWeatherResponse(
        @Query("query") query: String
    ) : Response<List<WeatherLocal>>

    @GET("api/location/{woeid}")
    suspend fun getWeatherDetail(
        @Path("woeid") woeid: Int
    ) : WeatherDetail
}