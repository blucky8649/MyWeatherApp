package com.example.myweatherapp.data.source.local

import androidx.room.TypeConverter
import com.example.myweatherapp.model.ConsolidatedWeather
import com.google.gson.Gson

class WeatherConverters {
    val gson = Gson()
    @TypeConverter
    fun fromConsolidatedWeaterToJson(consolidatedWeather: List<ConsolidatedWeather>): String {
        return gson.toJson(consolidatedWeather)
    }

    @TypeConverter
    fun fromJsonToConsolidatedWeater(consolidatedWeather: String): List<ConsolidatedWeather> {
        return gson.fromJson(consolidatedWeather, Array<ConsolidatedWeather>::class.java).toList()
    }
}