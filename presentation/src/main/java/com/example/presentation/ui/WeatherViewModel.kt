package com.example.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.entity.Weather
import com.example.domain.usecases.GetWeatherListUseCase
import com.example.domain.usecases.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModel() {

    val _state: MutableStateFlow<Resource<List<Weather>>> = MutableStateFlow(Resource.Loading())
    val state get() = _state

    init {
        observeWeather(fetchFromRemote = false)
    }
    suspend fun getWeatherFlow(
        query: String = "se",
        fetchFromRemote: Boolean
    ): StateFlow<WeatherUiState> {
        return getWeatherListUseCase(query, fetchFromRemote)
            .onStart { emit(Resource.Loading()) }
            .map { weather -> produceWeatherUiState(weather) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(StopTimeoutMillis),
                initialValue = WeatherUiState(isLoading = true)
            )
    }
    private val StopTimeoutMillis: Long = 5000

    fun produceWeatherUiState(taskLoad: Resource<List<Weather>>): WeatherUiState =
        when (taskLoad) {
            is Resource.Loading -> {
                WeatherUiState(isLoading = true)
            }
            is Resource.Success -> {
                taskLoad.data?.let {
                    WeatherUiState(
                        isLoading = false,
                        isRefreshing = false,
                        WeatherList = it
                    )
                } ?: WeatherUiState(isLoading = false, errorMassages = listOf("Error Occured"))
            }
            else -> { WeatherUiState(isLoading = false, errorMassages = listOf("Error Occured")) }
        }

    fun observeWeather(
        query: String = "se",
        fetchFromRemote: Boolean
    ) = viewModelScope.launch {
        getWeatherListUseCase(query, fetchFromRemote).collectLatest { result ->
            _state.emit(result)
        }
    }
}