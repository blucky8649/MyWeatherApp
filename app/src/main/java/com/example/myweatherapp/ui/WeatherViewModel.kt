package com.example.myweatherapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.WeatherRepository
import com.example.myweatherapp.model.WeatherLocal
import com.example.myweatherapp.model.entity.WeatherEntity
import com.example.myweatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    val _state: MutableStateFlow<Resource<List<WeatherEntity>>> = MutableStateFlow(Resource.Loading())
    val state get() = _state

    init {
        observeWeather(fetchFromRemote = false)
    }

    fun observeWeather(
        query: String = "se",
        fetchFromRemote: Boolean
    ) = viewModelScope.launch {
        repository.observeWeatherListings(query, fetchFromRemote).collectLatest { result ->
            _state.emit(result)
        }
    }
}