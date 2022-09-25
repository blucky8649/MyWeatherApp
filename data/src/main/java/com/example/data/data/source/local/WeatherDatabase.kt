package com.example.data.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myweatherapp.model.entity.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 2
)
@TypeConverters(WeatherConverters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
}