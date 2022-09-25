package com.example.data.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweatherapp.model.entity.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(
        weatherLocals: List<WeatherEntity>
    )

    @Query("SELECT * FROM weather")
    fun getAll() : List<WeatherEntity>

    @Query("DELETE FROM weather")
    suspend fun clear()
}