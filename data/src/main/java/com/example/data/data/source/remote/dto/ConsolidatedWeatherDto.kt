package com.example.data.data.source.remote.dto

data class ConsolidatedWeatherDto(
    val applicable_date: String,
    val humidity: Int,
    val the_temp: Double,
    val weather_state_abbr: String,
    val weather_state_name: String
) {
    companion object {
        val emptyConsolidatedWeather = ConsolidatedWeatherDto(
            "",
            0, 0.0,
            "", ""
        )
    }

}