package com.example.myweatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherItems")
data class WeatherLocal(
    val latt_long: String,
    val location_type: String,
    val title: String,
    @PrimaryKey
    val woeid: Int
)