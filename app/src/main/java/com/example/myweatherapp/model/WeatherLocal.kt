package com.example.myweatherapp.model

data class WeatherLocal(
    val title: String,
    val woeid: Int
) {
    companion object {
        val emptyWeatherLocal = WeatherLocal("", 0)
    }
}