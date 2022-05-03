package com.example.myweatherapp.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweatherapp.databinding.ItemTitleBinding
import com.example.myweatherapp.databinding.ItemWeatherInfoBinding
import com.example.myweatherapp.model.ConsolidatedWeather
import com.example.myweatherapp.model.entity.WeatherEntity
import com.example.myweatherapp.util.getToday
import com.example.myweatherapp.util.getTomorrow

class WeatherListAdapter : ListAdapter<WeatherEntity, RecyclerView.ViewHolder>(differCallback) {
    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<WeatherEntity>() {
            override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
                return oldItem.woeid == newItem.woeid
            }

            override fun areContentsTheSame(
                oldItem: WeatherEntity,
                newItem: WeatherEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> {
                WeatherTitleViewHolder(
                    ItemTitleBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                WeatherViewHolder(
                    ItemWeatherInfoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1 else 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is WeatherViewHolder -> holder.bind(getItem(position))
            else -> (holder as WeatherTitleViewHolder).bind(getItem(position))

        }
    }
}

class WeatherTitleViewHolder(val binding: ItemTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: WeatherEntity) {
        binding.apply {
            val todayInfo = item.weatherInfos.filter {
                it.applicable_date == getToday()
            }[0]
            tvCurrentDate.text = "(${todayInfo.applicable_date})"
        }
    }
}

class WeatherViewHolder(val binding: ItemWeatherInfoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: WeatherEntity) {

        val todayInfo = item.weatherInfos.filter {
            it.applicable_date == getToday()
        }[0]
        val tomorrowInfo = item.weatherInfos.filter {
            it.applicable_date == getTomorrow()
        }[0]

        binding.apply {
            tvLocalTitle.text = item.localName

            /**
             * Today
             */
            Glide.with(root)
                .load("https://www.metaweather.com/static/img/weather/png/${todayInfo.weather_state_abbr}.png")
                .centerCrop()
                .into(ivWeatherImageToday)
            tvHumidityToday.text = "${todayInfo.humidity}%"
            tvTempToday.text = "${todayInfo.the_temp.toInt()}℃"
            tvWeatherStateToday.text = todayInfo.weather_state_name

            /**
             * Tomorrow
             */
            Glide.with(root)
                .load("https://www.metaweather.com/static/img/weather/png/${tomorrowInfo.weather_state_abbr}.png")
                .centerCrop()
                .into(ivWeatherImageTomorrow)
            tvHumidityTomorrow.text = "${tomorrowInfo.humidity}%"
            tvTempTomorrow.text = "${tomorrowInfo.the_temp.toInt()}℃"
            tvWeatherStateTomorrow.text = tomorrowInfo.weather_state_name
        }
    }

}