package com.example.myweatherapp.data.repository

import com.example.myweatherapp.data.WeatherRepository
import com.example.myweatherapp.data.source.local.WeatherLocalDataSource
import com.example.myweatherapp.data.source.remote.WeatherRemoteDataSource
import com.example.myweatherapp.model.WeatherLocal
import com.example.myweatherapp.model.entity.WeatherEntity
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class WeatherRepositoryImpl(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override suspend fun observeWeatherListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<WeatherEntity>>> {
        return flow {
            emit(Resource.Loading())
            val localData = CoroutineScope(Dispatchers.IO).async { weatherLocalDataSource.getWeather() }.await()
            val shouldLoadFromCache = localData.isNotEmpty() && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Success(localData))
                return@flow
            }

            val remoteDataResponse = weatherRemoteDataSource.getWeatherLocal(query)
            val remoteWeatherDetail =
            if (remoteDataResponse.isSuccessful) {
                remoteDataResponse.body()?.let { listings ->
                    weatherLocalDataSource.clear()
                    val newList = listings.map { weatherLocal ->
                        WeatherEntity(
                            woeid = weatherLocal.woeid,
                            localName = weatherLocal.title,
                            weatherInfos =
                                weatherRemoteDataSource
                                    .getWeatherDetail(weatherLocal.woeid)
                                    .consolidated_weather
                        )
                    }
                    weatherLocalDataSource.insert(newList)
                    val localData = CoroutineScope(Dispatchers.IO).async { weatherLocalDataSource.getWeather() }.await()
                    emit(Resource.Success(
                        localData
                    ))
                }
            } else {
                emit(Resource.Error("Couldn't load data from remote"))
            }
        }
    }
}