package com.example.data.data.source.local

import androidx.room.TypeConverter
import com.example.data.data.source.remote.dto.ConsolidatedWeatherDto
import com.google.gson.Gson

class WeatherConverters {
    val gson = Gson()
    @TypeConverter
    fun fromConsolidatedWeaterToJson(consolidatedWeather: List<ConsolidatedWeatherDto>): String {
        return gson.toJson(consolidatedWeather)
    }

    @TypeConverter
    fun fromJsonToConsolidatedWeater(consolidatedWeather: String): List<ConsolidatedWeatherDto> {
        return gson.fromJson(consolidatedWeather, Array<ConsolidatedWeatherDto>::class.java).toList()
    }
}