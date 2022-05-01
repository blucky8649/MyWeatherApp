package com.example.myweatherapp.data.repository

import com.example.myweatherapp.data.WeatherRepository
import com.example.myweatherapp.data.source.local.WeatherLocalDataSource
import com.example.myweatherapp.data.source.remote.WeatherRemoteDataSource
import com.example.myweatherapp.model.WeatherItem
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(
    private val weatherLocalDataSource: WeatherLocalDataSource,
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {

    override suspend fun observeWeatherListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<WeatherItem>>> {
        return flow {
            emit(Resource.Loading())
            val localData = weatherLocalDataSource.getWeather()
            emit(Resource.Success(localData))
            val shouldLoadFromCache = localData.isNotEmpty() && !fetchFromRemote
            if (shouldLoadFromCache) {
                return@flow
            }

            val remoteDataResponse = weatherRemoteDataSource.getWeather(query)
            if (remoteDataResponse.isSuccessful) {
                remoteDataResponse.body()?.let { listings ->
                    weatherLocalDataSource.clear()
                    weatherLocalDataSource.insert(listings)
                    emit(Resource.Success(
                        weatherLocalDataSource.getWeather()
                    ))
                }
            } else {
                emit(Resource.Error("Couldn't load data from remote"))
            }
        }
    }
}