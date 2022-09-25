package com.example.data.data.source.repository

import com.example.domain.Resource
import com.example.domain.entity.Weather
import com.example.domain.repository.WeatherRepository
import com.example.data.data.source.local.LocalDataSource
import com.example.data.data.source.remote.RemoteDataSource
import com.example.myweatherapp.model.entity.WeatherEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor (
    private val weatherLocalDataSource: LocalDataSource,
    private val weatherRemoteDataSource: RemoteDataSource
) : WeatherRepository {

    override suspend fun observeWeatherListings(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Weather>>> {
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