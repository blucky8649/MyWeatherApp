package com.example.myweatherapp.model

data class WeatherLocal(
    val latt_long: String,
    val location_type: String,
    val title: String,
    val woeid: Int
)