package com.example.myweatherapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweatherapp.model.ConsolidatedWeather

@Entity(tableName = "weather")
data class WeatherEntity (
    @PrimaryKey
    val woeid: Int,
    val localName: String,
    val weatherInfos: List<ConsolidatedWeather>
)