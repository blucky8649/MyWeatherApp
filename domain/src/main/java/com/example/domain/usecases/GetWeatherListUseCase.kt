package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.entity.Weather
import com.example.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

data class WeatherUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val WeatherList: List<Weather> = emptyList(),
    val errorMassages: List<String> = emptyList()
)

@Singleton
class GetWeatherListUseCase @Inject constructor(
    val weatherListRepository: WeatherRepository
) {
    suspend operator fun invoke(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Weather>>> {
        return weatherListRepository.observeWeatherListings(query, fetchFromRemote)
    }
}