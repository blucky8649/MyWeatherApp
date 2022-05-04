package com.example.myweatherapp.model

data class WeatherDetail(
    val consolidated_weather: List<ConsolidatedWeather>,
    val location_type: String,
    val title: String,
    val woeid: Int
) {
    companion object {
        val emptyWeatherDetail = WeatherDetail(
            emptyList(),
            "",
            "",
            0
        )
    }
}