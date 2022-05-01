package com.example.myweatherapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myweatherapp.model.WeatherItem

@Database(
    entities = [WeatherItem::class],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}