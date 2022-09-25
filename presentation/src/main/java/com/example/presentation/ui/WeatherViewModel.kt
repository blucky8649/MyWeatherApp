package com.example.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.entity.Weather
import com.example.domain.usecases.GetWeatherListUseCase
import com.example.domain.usecases.WeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherListUseCase: GetWeatherListUseCase
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherUiState())
    val weatherState get() = _weatherState as StateFlow<WeatherUiState>

    val _state: MutableStateFlow<Resource<List<Weather>>> = MutableStateFlow(Resource.Loading())
    val state get() = _state

    init {
        observeWeather(fetchFromRemote = false)
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