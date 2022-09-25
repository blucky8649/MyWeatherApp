package com.example.data.data.source.remote.dto

data class WeatherDetailDto(
    val consolidated_weather: List<ConsolidatedWeatherDto>,
    val location_type: String,
    val title: String,
    val woeid: Int
) {
    companion object {
        val emptyWeatherDetail = WeatherDetailDto(
            emptyList(),
            "",
            "",
            0
        )
    }
}