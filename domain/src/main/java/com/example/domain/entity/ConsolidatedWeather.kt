package com.example.domain.entity

data class ConsolidatedWeather(
    val applicable_date: String,
    val humidity: Int,
    val the_temp: Double,
    val weather_state_abbr: String,
    val weather_state_name: String
) {
    companion object {
        val emptyConsolidatedWeather = ConsolidatedWeather(
            "",
            0, 0.0,
            "", ""
        )
    }

}