package com.example.myweatherapp.data.source.remote

import com.example.myweatherapp.model.WeatherItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("api/location/search")
    suspend fun getWeatherResponse(
        @Query("query")
        query: String
    ) : Response<List<WeatherItem>>
}