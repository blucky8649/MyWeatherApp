package com.example.domain.entity

data class Weather(
    val woeid: Int,
    val localName: String,
    val weatherInfos: List<ConsolidatedWeather>
)
