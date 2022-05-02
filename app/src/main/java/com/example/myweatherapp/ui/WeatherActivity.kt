package com.example.myweatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myweatherapp.adapter.WeatherListAdapter
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    val weatherAdapter: WeatherListAdapter by lazy {
        WeatherListAdapter()
    }
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.state.collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        weatherAdapter.submitList(result.data?.toList())
                    }
                }
            }
        }
    }
    fun setupRecyclerView() {
        binding.rvWeatherInfos.apply {
            adapter = weatherAdapter
            layoutManager = LinearLayoutManager(this@WeatherActivity)
        }
    }
}