package com.example.data.data.source.mapper

import com.example.data.data.source.remote.dto.ConsolidatedWeatherDto
import com.example.domain.entity.ConsolidatedWeather
import com.example.domain.entity.Weather
import com.example.myweatherapp.model.entity.WeatherEntity

fun WeatherEntity.toWeather(): Weather {
    return Weather(
        woeid = this.woeid,
        localName = this.localName,
        weatherInfos = this.weatherInfos.map { it.toWeather() }
    )
}
fun ConsolidatedWeatherDto.toWeather(): ConsolidatedWeather {
    return ConsolidatedWeather(
        applicable_date, humidity, the_temp, weather_state_abbr, weather_state_name
    )
}

