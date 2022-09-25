package com.example.data.data.source.remote.dto

data class WeatherDto(
    val title: String,
    val woeid: Int
) {
    companion object {
        val emptyWeatherLocal = WeatherDto("", 0)
    }
}