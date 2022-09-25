package com.example.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.Resource
import com.example.presentation.adapter.WeatherListAdapter
import com.example.presentation.databinding.ActivityMainBinding
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
        setupSwipeRefreshLayout()
        observeWeatherState()

    }
    fun observeWeatherState() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        weatherAdapter.submitList(result.data?.toList())
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }
    fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.observeWeather(fetchFromRemote = true)
                isRefreshing = false
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