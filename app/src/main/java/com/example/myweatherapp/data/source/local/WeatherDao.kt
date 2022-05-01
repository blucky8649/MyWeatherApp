package com.example.myweatherapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweatherapp.model.WeatherItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        weatherItems: List<WeatherItem>
    )

    @Query("SELECT * FROM weatherItems")
    fun getAll() : List<WeatherItem>

    @Query("DELETE FROM weatherItems")
    suspend fun clear()
}