package com.example.myweatherapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.data.source.remote.dto.ConsolidatedWeatherDto

@Entity(tableName = "weather")
data class WeatherEntity (
    @PrimaryKey
    val woeid: Int,
    val localName: String,
    val weatherInfos: List<ConsolidatedWeatherDto>
)