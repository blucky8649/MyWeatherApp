package com.example.myweatherapp.data.source.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.myweatherapp.model.ConsolidatedWeather
import com.example.myweatherapp.model.entity.WeatherEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var weatherDao: WeatherDao
    private lateinit var weatherDatebase: WeatherDatabase
    private val weatherInfoList = ArrayList<WeatherEntity>()

    @Before
    fun setUp() {
        weatherDatebase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).build()
        weatherDao = weatherDatebase.getWeatherDao()

        weatherInfoList.add(WeatherEntity(1, "seoul", listOf()))
        weatherInfoList.add(WeatherEntity(2, "tokyo", listOf()))
        weatherInfoList.add(WeatherEntity(3, "undefined", listOf()))
        weatherInfoList.add(WeatherEntity(4, "gggggg", listOf()))
    }

    @After
    fun close() {
        weatherDatebase.close()
    }

    @Test
    fun insertWeatherMultipleTimesTest() = runBlocking {
        weatherDao.insertAll(weatherInfoList)
        weatherDao.insertAll(weatherInfoList)
        weatherDao.insertAll(weatherInfoList)
        weatherDao.insertAll(weatherInfoList)
        weatherDao.insertAll(weatherInfoList)
        val weatherInfos = weatherDao.getAll()
        Truth.assertThat(weatherInfos).isEqualTo(weatherInfoList)
    }
    @Test
    fun clearWeatherInfoTest() = runBlocking {
        weatherDao.clear()
        val weatherInfos = weatherDao.getAll()
        Truth.assertThat(weatherInfos).isEmpty()
    }

}