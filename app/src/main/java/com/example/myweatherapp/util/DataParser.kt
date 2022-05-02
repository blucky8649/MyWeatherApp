package com.example.myweatherapp.util

import com.example.myweatherapp.model.ConsolidatedWeather
import com.example.myweatherapp.model.entity.WeatherEntity
import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringFormat(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return simpleDateFormat.format(this)
}
