package com.example.myweatherapp.model

import androidx.room.Entity

@Entity(tableName = "weatherItems")
data class WeatherItem(
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)