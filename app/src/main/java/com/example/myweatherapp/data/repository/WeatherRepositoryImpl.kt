package com.example.myweatherapp.data.repository

import android.util.Log
import com.example.myweatherapp.data.WeatherRepository
import com.example.myweatherapp.data.source.local.LocalDataSource
import com.example.myweatherapp.data.source.local.WeatherLocalDataSource
import com.example.myweatherapp.data.source.remote.RemoteDataSource
import com.example.myweatherapp.data.source.remote.WeatherRemoteDataSource
import com.example.myweatherapp.model.WeatherLocal
import com.example.myweatherapp.model.entity.WeatherEntity
import com.example.myweatherapp.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.system.measureTimeMillis

class WeatherRepositoryImpl(
    private val weatherLocalDataSource: LocalDataSource,
    private val weatherRemoteDataSource: RemoteDataSource
) : WeatherRepository {

    override suspend fun observeWeatherListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<WeatherEntity>>> {
        return flow {
            emit(Resource.Loading())
            val localData =
                CoroutineScope(Dispatchers.IO).async { weatherLocalDataSource.getWeather() }.await()
            val shouldLoadFromCache = localData.isNotEmpty() && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Success(localData))
                return@flow
            }

            val remoteDataResponse = weatherRemoteDataSource.getWeatherLocal(query)
            if (remoteDataResponse.isSuccessful) {
                remoteDataResponse.body()?.let { listings ->
                    weatherLocalDataSource.clear()
                    val newList = listings.map { weatherLocal ->
                        CoroutineScope(Dispatchers.IO).async {
                            WeatherEntity(
                                woeid = weatherLocal.woeid,
                                localName = weatherLocal.title,
                                weatherInfos = weatherRemoteDataSource // 네트워크 요청
                                    .getWeatherDetail(weatherLocal.woeid)
                                    .consolidated_weather
                            )
                        }

                    }.awaitAll()
                    weatherLocalDataSource.insert(newList)
                    val localData =
                        CoroutineScope(Dispatchers.IO)
                            .async { weatherLocalDataSource.getWeather() }
                            .await()
                    emit(
                        Resource.Success(
                            localData
                        )
                    )
                }
            } else {
                emit(Resource.Error("Couldn't load data from remote"))
            }
        }
    }
}